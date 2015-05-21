package tudelft.ti2806.pl3.visualization;

import tudelft.ti2806.pl3.data.filter.Filter;
import tudelft.ti2806.pl3.data.graph.node.DataNode;
import tudelft.ti2806.pl3.visualization.wrapper.WrappedGraphData;

import java.util.List;

public interface GraphModelInterface {
	
	void produceGraph(List<Filter<DataNode>> filters);
	
	WrappedGraphData getGraphData();
}
