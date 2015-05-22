package tudelft.ti2806.pl3.visualization;

import tudelft.ti2806.pl3.visualization.wrapper.DataNodeWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.NodeWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.operation.collapse.CollapseOnInterest;
import tudelft.ti2806.pl3.visualization.wrapper.operation.interest.ConstructInterestList;
import tudelft.ti2806.pl3.visualization.wrapper.operation.unwrap.Unwrap;

import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * This Model contains the data after a zoom has been performed.
 * <p>It listens to the {@link tudelft.ti2806.pl3.visualization.FilteredGraphModel} for changes.
 * If this model has updated his data, this class will also update his data and notify the view.</p>
 * <p>We can also just alter the zoom level on this class and then it will recalculate the data and
 * notify the view.</p>
 * <p>Recalculation of the data means, that it takes the collapsedNode from the
 * {@link tudelft.ti2806.pl3.visualization.FilteredGraphModel} and filters them based on the interest value.
 * Then it unwraps the collapsed node and notifies the view.</p>
 * Created by Boris Mattijssen on 20-05-15.
 */
public class ZoomedGraphModel extends Observable implements Observer {

	private FilteredGraphModel filteredGraphModel;
	private NodeWrapper collapsedNode;
	private List<DataNodeWrapper> dataNodeWrapperList;

	private int zoomLevel = 0;

	/**
	 * Construct a new ZoomedGraphModel, with a reference to the {@link tudelft.ti2806.pl3.data.filter.Filter}.
	 * @param filteredGraphModel
	 *          The {@link tudelft.ti2806.pl3.visualization.FilteredGraphModel}
	 */
	public ZoomedGraphModel(FilteredGraphModel filteredGraphModel) {
		this.filteredGraphModel = filteredGraphModel;

	}

	public List<DataNodeWrapper> getDataNodeWrapperList() {
		return dataNodeWrapperList;
	}

	/**
	 * Sets the zoom level, only if the zoom level is larger then 0.
	 * @param zoomLevel
	 *          the new zoom level
	 */
	public void setZoomLevel(int zoomLevel) {
		if (zoomLevel > 0) {
			this.zoomLevel = zoomLevel;
		}
	}

	public int getZoomLevel() {
		return zoomLevel;
	}

	/**
	 * Produces the data needed to display the graph.
	 *
	 * <p>It first construct a list of all interests in the graph
	 *
	 * <p>It will then determine the amount of nodes to display
	 *
	 * <p>It will then use the {@link CollapseOnInterest} operation
	 * to collapse all uninteresting nodes
	 *
	 * <p>It will then unwrap these nodes and notify the view
	 */
	public void produceDataNodeWrapperList() {
		ConstructInterestList constructInterestList = new ConstructInterestList();
		constructInterestList.calculate(collapsedNode, null);
		constructInterestList.getInterests().sort(Collections.reverseOrder());
		int numberOfNodes = (int) (Math.pow(2,zoomLevel) * 10);

		CollapseOnInterest collapseOnInterest = new CollapseOnInterest(
				constructInterestList.getInterests().get(Math.min(numberOfNodes,
						constructInterestList.getInterests().size() - 1)));
		collapseOnInterest.calculate(collapsedNode, null);
		
		Unwrap unwrap = new Unwrap(collapsedNode);
		dataNodeWrapperList = unwrap.getDataNodeWrappers();

		setChanged();
		notifyObservers();
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o == filteredGraphModel) {
			collapsedNode = filteredGraphModel.getCollapsedNode();
			produceDataNodeWrapperList();
		}
	}
}
