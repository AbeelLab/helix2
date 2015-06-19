package tudelft.ti2806.pl3.visualization;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.swingViewer.View;
import org.graphstream.ui.swingViewer.Viewer;
import org.graphstream.ui.swingViewer.util.DefaultShortcutManager;
import tudelft.ti2806.pl3.ScreenSize;
import tudelft.ti2806.pl3.controls.MouseManager;
import tudelft.ti2806.pl3.data.gene.Gene;
import tudelft.ti2806.pl3.data.graph.DataNode;
import tudelft.ti2806.pl3.data.wrapper.Wrapper;
import tudelft.ti2806.pl3.data.wrapper.WrapperClone;
import tudelft.ti2806.pl3.exception.EdgeZeroWeightException;
import tudelft.ti2806.pl3.exception.NodeNotFoundException;
import tudelft.ti2806.pl3.util.observable.LoadingObservable;
import tudelft.ti2806.pl3.util.observers.LoadingObserver;

import java.awt.Component;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

/**
 * The GraphView is responsible for adding the nodes and edges to the graph, keeping the nodes and edges on the right
 * positions and applying the right style to the graph.
 *
 * @author Sam Smulders
 *
 */
public class GraphView implements Observer, tudelft.ti2806.pl3.View, ViewInterface, LoadingObservable {

	private static final float EDGE_THICKNESS_SCALE = 10f;
	
	/**
	 * The zoomLevel used to draw the graph.<br>
	 * A zoom level of 1.0 shows the graph 1:1, so that every base pair should be readable, each with pixels to draw its
	 * value as text. A zoom level of 2.0 shows the graph with each base pair using the half this size.
	 */
	private float zoomLevel = 1.0f;
	
	/**
	 * The css style sheet used drawing the graph.<br>
	 * Generate a new view to have the changes take effect.
	 */
	
	private List<WrapperClone> graphData;
	private final Graph graph = new SingleGraph("Graph");
	private Viewer viewer;
	private View panel;
	private final ArrayList<LoadingObserver> loadingObservers = new ArrayList<>();
	private MouseManager mouseManager;

	private final ArrayList<GraphLoadedListener> graphLoadedListeners = new ArrayList<>();

	private float offsetToCenter = -1;
	private boolean zoomCenterSet = false;
	private final ZoomedGraphModel zoomedGraphModel;

	private Gene selectedGene = null;

	/**
	 * Construct a GraphView.
	 *
	 * @param zoomedGraphModel
	 *            The zoomed graph model
	 */
	public GraphView(ZoomedGraphModel zoomedGraphModel) {
		this.zoomedGraphModel = zoomedGraphModel;
		graphData = new ArrayList<>();
		generateViewer();
	}
	
	/**
	 * Generates a {@link Viewer} for the graph with the given {@code zoomLevel} . A new Viewer should be constructed
	 * every time the graphData or zoomLevel updates.
	 */
	private void generateViewer() {
		viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_SWING_THREAD);
		panel = viewer.addDefaultView(false);
		mouseManager = new MouseManager();
		viewer.getDefaultView().setMouseManager(mouseManager);
		removeDefaultKeys();
		panel.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				setOffsetToCenter();
				setZoomCenter(offsetToCenter);
				notifyGraphLoadedListeners();
			}
		});
	}
	
	/**
	 * Remove the default keys from the GraphStream library, since we use our own. There is no other way than this to do
	 * it.
	 */
	public void removeDefaultKeys() {
		DefaultShortcutManager listener = (DefaultShortcutManager) this.getPanel().getKeyListeners()[0];
		listener.release();
	}
	
	/**
	 * Sets the graph its drawing properties.
	 */
	private void setGraphPropertys() {
		final String stylesheet = "stylesheet.css";
		
		graph.addAttribute("ui.quality");
		graph.addAttribute("ui.antialias");
		graph.addAttribute("ui.stylesheet", "url('" + stylesheet + "')");
	}
	
	/**
	 * Generates a Graph from the current graphData.
	 */
	public void generateGraph() {
		notifyLoadingObservers(true);
		graph.clear();
		setGraphPropertys();
		final double someSize = panel.getBounds().height
			/ (panel.getBounds().width * zoomLevel / zoomedGraphModel.getWrappedCollapsedNode().getWidth())
			/ zoomedGraphModel.getWrappedCollapsedNode().getGenome().size();
		graphData.forEach(node -> {
				Node graphNode = graph.addNode(Integer.toString(node.getId()));
				double y = node.getY() * someSize;
				graphNode.setAttribute("xy", node.getX(), y);
				if (node.canUnwrap()) {
					graphNode.addAttribute("ui.class", "BOX");
				}
				graphNode.addAttribute("ui.label",
						NumberFormat.getNumberInstance(Locale.US)
								.format(node.getOriginalNode().getBasePairCount()));
				graphNode.setAttribute("node", node);
		});
		
		for (WrapperClone node : graphData) {
			int i = 0;
			for (Wrapper to : node.getOutgoing()) {
				if (node.getId() >= 0 && to.getId() >= 0) { // Exclude FixWrappers
					try {
						addNormalEdge(graph, node, to, i);
					} catch (EdgeZeroWeightException e) {
						e.printStackTrace();
					}
				}
				i++;
			}
		}
		colorGene();
		notifyLoadingObservers(false);
	}
	
	/**
	 * Adds an edge between two nodes.
	 * 
	 * @param graph
	 *            the graph to add the edge to
	 * @param from
	 *            the node where the edge begins
	 * @param to
	 *            the node where the edge ends
	 * @param i
	 *            the index of the edge in the list of outgoing edges
	 */
	@SuppressWarnings("PMD.UnusedPrivateMethod")
	private void addNormalEdge(Graph graph, Wrapper from, Wrapper to, int i) throws EdgeZeroWeightException {
		Edge edge = graph.addEdge(from.getId() + "-" + to.getId(), Integer.toString(from.getId()),
				Integer.toString(to.getId()), true);
		int weight = from.getOutgoingWeight().get(i);
		float percent = ((float) weight) / ((float) zoomedGraphModel.getGenomesCount());
		
		if (weight == 0) {
			edge.addAttribute("ui.label", "fix me!");
			throw new EdgeZeroWeightException("The weight of the edge from " + from + " to " + to + " cannot be 0.");
		} else {
			edge.addAttribute("ui.style", "size: " + (percent * EDGE_THICKNESS_SCALE) + "px;");
		}
	}
	
	@Override
	public Component getPanel() {
		return panel;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if (o == zoomedGraphModel) {
			graphData = zoomedGraphModel.getDataNodeWrapperList();
			zoomLevel = zoomedGraphModel.getZoomLevel();
			generateGraph();
			zoom();
			centerGraph();
		}
	}
	
	private void centerGraph() {
		if (!zoomCenterSet) {
			setZoomCenter(0);
			setOffsetToCenter();
			setZoomCenter(offsetToCenter);
			zoomCenterSet = true;
		}
	}
	
	private void zoom() {
		viewer.getDefaultView().getCamera().setViewPercent(1 / zoomLevel);
	}
	
	public float getZoomCenter() {
		return (float) viewer.getDefaultView().getCamera().getViewCenter().x;
	}
	
	public double getViewPercent() {
		return viewer.getDefaultView().getCamera().getViewPercent();
	}
	
	/**
	 * Set the offset of the center of the graph to the left edge of the screen.
	 */
	public void setOffsetToCenter() {
		Point3 point3 = viewer.getDefaultView().getCamera()
				.transformPxToGu(0, ScreenSize.getInstance().getHeight() / 2d);
		offsetToCenter = (float) point3.x * -1;
	}
	
	public float getOffsetToCenter() {
		return offsetToCenter;
	}
	
	/**
	 * Moves the view to the given position on the x axis.
	 *
	 * @param zoomCenter
	 *            the new center of view
	 */
	public void setZoomCenter(float zoomCenter) {
		viewer.getDefaultView().getCamera().setViewCenter(zoomCenter, 0, 0);
	}
	
	/**
	 * Centers the graph on a specific node. It passes a {@link DataNode} and then looks in the list of currently
	 * drawn  {@link WrapperClone}s, which one contains this {@link DataNode} and then sets the zoom center on this
	 * {@link WrapperClone}.
	 *
	 * @param node
	 *            The {@link DataNode} to move the view to
	 * @param selected
	 *      The gene to highlight on the graph
	 * @throws NodeNotFoundException
	 *             Thrown when the node cannot be found in all {@link WrapperClone}s
	 */
	public void centerOnNode(DataNode node, Gene selected) throws NodeNotFoundException {
		notifyLoadingObservers(true);
		float x = -1;
		selectedGene = selected;
		colorGene();
		for (WrapperClone wrapperClone : graphData) {
			if (wrapperClone.getDataNodes().contains(node)) {
				x = wrapperClone.getX();
				break;
			}
		}
		notifyLoadingObservers(false);
		if (x != -1) {
			setZoomCenter(x);
		} else {
			throw new NodeNotFoundException("The node " + node
					+ " you are looking for cannot be found in the current graph.");
		}
	}

	/**
	 * Color the selected gene on the graph.
	 */
	private void colorGene() {
		if (selectedGene != null) {
			for (Node graphNode : graph.getNodeSet()) {
				graphNode.removeAttribute("ui.style");
				WrapperClone wrapper = graphNode.getAttribute("node", WrapperClone.class);
				wrapper.getLabels().stream()
						.filter(label -> label.getText().equals(selectedGene.getName()))
						.forEach(label -> graphNode.addAttribute("ui.style",
								"fill-color: red;"));
			}
		}
	}

	public double getGraphDimension() {
		return viewer.getDefaultView().getCamera().getGraphDimension();
	}
	
	@Override
	public void addLoadingObserver(LoadingObserver loadingObservable) {
		loadingObservers.add(loadingObservable);
	}
	
	@Override
	public void addLoadingObserversList(ArrayList<LoadingObserver> loadingObservers) {
		for (LoadingObserver loadingObserver : loadingObservers) {
			addLoadingObserver(loadingObserver);
		}
	}
	
	@Override
	public void deleteLoadingObserver(LoadingObserver loadingObservable) {
		loadingObservers.remove(loadingObservable);
	}
	
	@Override
	public void notifyLoadingObservers(Object arguments) {
		for (LoadingObserver loadingObserver : loadingObservers) {
			loadingObserver.update(this, arguments);
		}
	}
	
	public void addGraphLoadedListener(GraphLoadedListener listener) {
		graphLoadedListeners.add(listener);
	}
	
	public void removeGraphLoadedListener(GraphLoadedListener listener) {
		graphLoadedListeners.remove(listener);
	}
	
	public void notifyGraphLoadedListeners() {
		graphLoadedListeners.forEach(GraphLoadedListener::graphLoaded);
	}
	
	public void removeDetailView() {
		mouseManager.removeDetailView();
	}
}
