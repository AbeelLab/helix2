package tudelft.ti2806.pl3.controls;

import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.graphicGraph.GraphicGraph;
import org.graphstream.ui.swingViewer.View;
import org.graphstream.ui.swingViewer.util.DefaultMouseManager;
import tudelft.ti2806.pl3.data.wrapper.WrapperClone;
import tudelft.ti2806.pl3.detailView.DetailView;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * This MouseManager extends the functionality of the default mouse actions of GraphStream.
 * Created by Mathieu Post on 8-6-15.
 */
public class MouseManager extends DefaultMouseManager {
	private static final int MARGIN = 5;

	WrapperClone node = null;
	DetailView detailView;

	public MouseManager() {
		detailView = new DetailView();
	}

	@Override
	public void init(GraphicGraph graph, View view) {
		super.init(graph, view);
		view.setLayout(null);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		super.mouseMoved(e);
		int x = e.getX();
		int y = e.getY();
		mouseMoved(x, y);
	}

	/**
	 * Make or delete the DetailView based on mouse location.
	 * @param x
	 * 		x location of the mouse cursor.
	 * @param y
	 * 		y location of the mouse cursor.
	 */
	public void mouseMoved(int x, int y) {
		ArrayList<GraphicElement> graphicElements = view.allNodesOrSpritesIn(
				x - MARGIN, y - MARGIN, x + MARGIN, y + MARGIN);
		if (graphicElements.size() == 0) {
			removeDetailView();
		} else {
			GraphicElement element = graphicElements.get(0);
			WrapperClone wrapper = element.getAttribute("node", WrapperClone.class);
			if (node != wrapper) {
				node = wrapper;
				view.add(detailView, BorderLayout.WEST);
				detailView.setNode(node, x, y);
				view.updateUI();
			}
		}
	}

	/**
	 * Removes the DetailView and sets the current node to null.
	 */
	public void removeDetailView() {
		node = null;
		view.remove(detailView);
		view.updateUI();
	}
}
