package tudelft.ti2806.pl3.visualization.position.wrapper;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.visualization.position.WrapperOperation;

import java.util.ArrayList;
import java.util.List;

public class VerticalWrapper extends CombineWrapper {
	
	public VerticalWrapper(List<NodeWrapper> nodeList) {
		super(nodeList);
	}
	
	@Override
	public long getWidth() {
		long max = Integer.MIN_VALUE;
		for (NodeWrapper node : nodeList) {
			max = Math.max(max, node.getWidth());
		}
		return max;
	}
	
	@Override
	public long getXStart() {
		long min = Long.MAX_VALUE;
		for (NodeWrapper node : nodeList) {
			min = Math.min(node.getXStart(), min);
		}
		return min;
	}
	
	@Override
	public long getXEnd() {
		long max = Long.MIN_VALUE;
		for (NodeWrapper node : nodeList) {
			max = Math.max(node.getXEnd(), max);
		}
		return max;
	}
	
	@Override
	public List<Genome> getGenome() {
		List<Genome> genome = new ArrayList<Genome>();
		for (NodeWrapper node : nodeList) {
			genome.addAll(node.getGenome());
		}
		return genome;
	}

	@Override
	public void calculate(WrapperOperation wrapperOperation) {
		wrapperOperation.calculate(this);
	}
}
