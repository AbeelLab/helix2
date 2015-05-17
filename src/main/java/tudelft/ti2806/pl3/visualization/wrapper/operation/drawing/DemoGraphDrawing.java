package tudelft.ti2806.pl3.visualization.wrapper.operation.drawing;

import tudelft.ti2806.pl3.visualization.wrapper.HorizontalWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.NodePosition;
import tudelft.ti2806.pl3.visualization.wrapper.NodeWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.SingleWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.SpaceWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.VerticalWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.operation.WrapperOperation;

public class DemoGraphDrawing implements WrapperOperation {
	// private Graph graph;
	
	// public DemoGraphDrawing(Graph graph) {
	// this.graph = graph;
	// }
	
	@Override
	public void calculate(HorizontalWrapper wrapper) {
		if (!wrapper.isCollapsed()) {
			for (NodeWrapper node : wrapper.getNodeList()) {
				calculate(node);
			}
		}
	}
	
	@Override
	public void calculate(VerticalWrapper wrapper) {
		for (NodeWrapper node : wrapper.getNodeList()) {
			calculate(node);
		}
	}
	
	@Override
	public void calculate(SpaceWrapper wrapper) {
		for (NodeWrapper node : wrapper.getNodeList()) {
			calculate(node);
		}
	}
	
	@Override
	public void calculate(SingleWrapper wrapper) {
		calculate(wrapper.getNode());
	}
	
	@Override
	public void calculate(NodePosition wrapper) {
		
	}
}
