package tudelft.ti2806.pl3.util;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.graph.DataNode;
import tudelft.ti2806.pl3.data.graph.Edge;
import tudelft.ti2806.pl3.data.wrapper.WrappedGraphData;
import tudelft.ti2806.pl3.data.wrapper.Wrapper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * An utility class to find and/or remove dead edges from a {@link GraphData}
 * object.
 * 
 * @author Sam Smulders
 * @author Boris Mattijssen
 */
public class EdgeUtil {
	private EdgeUtil() {
	}
	
	/**
	 * This method removes all "empty" edges from the graph. Empty edges are
	 * edges that are left over after filtering, but create a path that is
	 * actually to short. A longer path exists that should be the only path. For
	 * an example of such a dead edge, see the data/testdata/emptyEdges folder.
	 * 
	 * @param wrappedGraphData
	 *            The wrapped data containing all {@link Wrapper}s in the graph
	 */
	public static void removeAllEmptyEdges(WrappedGraphData wrappedGraphData) {
		for (Wrapper wrapper : wrappedGraphData.getPositionedNodes()) {
			if (wrapper.getOutgoing().size() <= 1) {
				continue;
			}
			List<Genome> genomes = new ArrayList<>(wrapper.getGenome());
			List<Wrapper> outgoingList = new ArrayList<>(wrapper.getOutgoing());
			outgoingList.sort(Comparator.<Wrapper> naturalOrder());
			List<Wrapper> removeList = new ArrayList<>();
			for (Wrapper outgoing : outgoingList) {
				if (genomes.size() == 0) {
					removeList.add(outgoing);
				} else {
					genomes.removeAll(outgoing.getGenome());
				}
			}
			for (Wrapper remove : removeList) {
				remove.getIncoming().remove(wrapper);
			}
			wrapper.getOutgoing().removeAll(removeList);
		}
	}
	
	/**
	 * Removes all edges of which one or both of their nodes is not on the
	 * graph.
	 * 
	 * @param edgeList
	 *            the list of edges in the graph
	 * @param nodeList
	 *            the list of nodes in the graph
	 */
	public static void removeAllDeadEdges(List<Edge> edgeList,
			List<DataNode> nodeList) {
		edgeList.removeAll(getAllDeadEdges(edgeList, nodeList));
	}
	
	/**
	 * Finds all the edges on the graph which have one or two nodes which are
	 * not on the graph.
	 * 
	 * @param edgeList
	 *            the list of edges in the graph
	 * @param nodeList
	 *            the list of nodes in the graph
	 * @return a list of all dead edges
	 */
	public static List<Edge> getAllDeadEdges(List<Edge> edgeList,
			List<DataNode> nodeList) {
		List<Edge> removeList = new ArrayList<Edge>();
		for (Edge edge : edgeList) {
			if (!nodeList.contains(edge.getFrom())
					|| !nodeList.contains(edge.getTo())) {
				removeList.add(edge);
			}
		}
		return removeList;
	}
}
