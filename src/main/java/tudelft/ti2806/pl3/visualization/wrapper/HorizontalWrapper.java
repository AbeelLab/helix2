package tudelft.ti2806.pl3.visualization.wrapper;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.visualization.wrapper.operation.WrapperOperation;

import java.util.ArrayList;
import java.util.List;

/**
 * A collection of horizontal combine able nodes. All nodes should be in a row
 * with only one connection and with the same set of genomes.
 * 
 * @author Mathieu Post
 * @author Sam Smulders
 */
public class HorizontalWrapper extends CombineWrapper {
	
	/**
	 * An collection of {@link SNodes} which can be used as a single SNode.
	 * 
	 * @param nodePosList
	 *            a connected and sorted list of edges.
	 */
	public HorizontalWrapper(List<NodeWrapper> nodePosList, boolean collapsed) {
		super(nodePosList, collapsed);
	}
	
	@Override
	public long getWidth() {
		long sum = 0;
		for (NodeWrapper node : nodeList) {
			sum += node.getWidth();
		}
		return sum;
	}
	
	@Override
	public long getXStart() {
		return getFirst().getXStart();
	}
	
	@Override
	public long getXEnd() {
		return getLast().getXEnd();
	}
	
	@Override
	public List<Genome> getGenome() {
		return getFirst().getGenome();
	}
	
	@Override
	public void calculate(WrapperOperation wrapperSequencer, NodeWrapper container) {
		wrapperSequencer.calculate(this, container);
	}

	@Override
	public HorizontalWrapper deepClone() {
		List<NodeWrapper> clonedList = new ArrayList<>(nodeList.size());
		nodeList.forEach(s -> clonedList.add(s.deepClone()));
		return new HorizontalWrapper(clonedList,isCollapsed());
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return new HorizontalWrapper(nodeList,isCollapsed());
	}
}
