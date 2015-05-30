package tudelft.ti2806.pl3.data.wrapper.operation.collapse;

import tudelft.ti2806.pl3.data.wrapper.CombineWrapper;
import tudelft.ti2806.pl3.data.wrapper.HorizontalWrapper;
import tudelft.ti2806.pl3.data.wrapper.SpaceWrapper;
import tudelft.ti2806.pl3.data.wrapper.VerticalWrapper;
import tudelft.ti2806.pl3.data.wrapper.Wrapper;
import tudelft.ti2806.pl3.data.wrapper.operation.WrapperOperation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CalculateCollapse extends WrapperOperation {
	private List<Float> list;
	
	/**
	 * Computes the distances between the nodes.
	 * 
	 * @param wrapper
	 *            the wrapped graph
	 */
	public void compute(Wrapper wrapper) {
		list = new ArrayList<>();
		list.add(Float.MAX_VALUE);
		calculate(wrapper, null);
		Collections.sort(list);
		Collections.sort(list, Collections.reverseOrder());
	}
	
	@Override
	public void calculate(HorizontalWrapper wrapper, Wrapper container) {
		super.calculate(wrapper, container);
		wrapper.addCollapse(getSpaceLeft(wrapper));
		addToList(wrapper);
	}
	
	@Override
	public void calculate(SpaceWrapper wrapper, Wrapper container) {
		super.calculate(wrapper, container);
		wrapper.addCollapse(getSpaceLeft(wrapper));
		addToList(wrapper);
	}
	
	@Override
	public void calculate(VerticalWrapper wrapper, Wrapper container) {
		super.calculate(wrapper, container);
		addToList(wrapper);
	}
	
	private void addToList(CombineWrapper wrapper) {
		for (int i = wrapper.getNodeList().size(); i > 1; i--) {
			list.add(wrapper.getCollapse());
		}
	}
	
	/**
	 * Calculates if there is enough space for a wrapper to unfold it.
	 * 
	 * @param wrapper
	 *            the wrapper of which to determine if there is enough space to
	 *            unfold it
	 * @return a value of the average space between nodes.
	 */
	float getSpaceLeft(CombineWrapper wrapper) {
		List<Wrapper> list = new ArrayList<>(wrapper.getNodeList());
		Collections.sort(list, new XComparator());
		float avg = 0;
		int count = list.size();
		for (int i = list.size() - 2; i >= 0; i--) {
			float left = list.get(i + 1).getX() - list.get(i).getX();
			if (left == 0) {
				count--;
			} else {
				avg += left;
			}
		}
		return avg / count;
	}
	
	public class XComparator implements Comparator<Wrapper> {
		@Override
		public int compare(Wrapper w1, Wrapper w2) {
			return (int) Math.signum(w1.getX() - w2.getX());
		}
	}
	
	public List<Float> getCollapses() {
		return list;
	}
}
