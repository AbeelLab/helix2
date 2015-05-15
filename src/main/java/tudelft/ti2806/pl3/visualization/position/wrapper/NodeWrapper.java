package tudelft.ti2806.pl3.visualization.position.wrapper;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.visualization.position.WrapperOperation;

import java.util.ArrayList;
import java.util.List;

public abstract class NodeWrapper {
	
	protected double yy;
	protected List<NodeWrapper> incoming = new ArrayList<NodeWrapper>();
	protected List<NodeWrapper> outgoing = new ArrayList<NodeWrapper>();
	protected int previousNodesCount = -1;
	
	public abstract long getXStart();
	
	public abstract long getXEnd();
	
	public abstract long getWidth();
	
	public int getPreviousNodesCount() {
		return previousNodesCount;
	}
	
	public double getY() {
		return yy;
	}
	
	/**
	 * Calculate the number of nodes on the longest path to this node.
	 * 
	 * @return the number of nodes on the longest path to this node
	 */
	public int calculatePreviousNodesCount() {
		if (this.getPreviousNodesCount() != -1) {
			return this.getPreviousNodesCount();
		}
		int max = 0;
		for (NodeWrapper incomingNode : this.getIncoming()) {
			max = Math.max(max, incomingNode.calculatePreviousNodesCount() + 1);
		}
		this.previousNodesCount = max;
		return this.previousNodesCount;
	}
	
	/**
	 * Calculates the whitespace available on the right side of this node.
	 * 
	 * @return the number of base pairs that fit in the whitespace on the right
	 *         side of the node.
	 */
	public long calculateWhitespaceOnRightSide() {
		long min = Long.MAX_VALUE;
		for (NodeWrapper incomingNode : this.getOutgoing()) {
			min = Math.min(min, incomingNode.getXStart());
		}
		return min - this.getXEnd();
	}
	
	public List<NodeWrapper> getIncoming() {
		return incoming;
	}
	
	public List<NodeWrapper> getOutgoing() {
		return outgoing;
	}

	public abstract String getIdString();

	public abstract List<Genome> getGenome();

	public abstract void calculate(WrapperOperation wrapperSequencer);
}
