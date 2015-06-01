package tudelft.ti2806.pl3.data.wrapper;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.graph.DataNode;
import tudelft.ti2806.pl3.data.wrapper.operation.WrapperOperation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

class TestWrapper extends Wrapper {
	private int basePairCount;
	private HashSet<Genome> genomeSet = new HashSet<>();
	
	public TestWrapper(int basePairCount, Genome... genomes) {
		this.basePairCount = basePairCount;
		for (Genome genome : genomes) {
			this.genomeSet.add(genome);
		}
	}
	
	public TestWrapper(int basePairCount) {
		this.basePairCount = basePairCount;
	}
	
	public TestWrapper() {
		this(0);
	}
	
	@Override
	public long getBasePairCount() {
		return basePairCount;
	}
	
	@Override
	public int getWidth() {
		return 1;
	}
	
	@Override
	public void calculateX() {
	}
	
	@Override
	public String getIdString() {
		return null;
	}
	
	@Override
	public Set<Genome> getGenome() {
		return genomeSet;
	}
	
	@Override
	public void calculate(WrapperOperation wrapperSequencer, Wrapper container) {
	}
	
	@Override
	public void collectDataNodes(List<DataNode> list) {
	}
}
