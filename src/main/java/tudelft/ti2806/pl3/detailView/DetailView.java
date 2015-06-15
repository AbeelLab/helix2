package tudelft.ti2806.pl3.detailView;

import tudelft.ti2806.pl3.data.wrapper.WrapperClone;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This view is added when more details about a node are needed.
 * @author mathieu
 */
public class DetailView extends JPanel {
	private static final int BORDER = 10;
	private static final int MAX_ROWS = 5;
	private static final int POSITION_OFFSET = 30;

	/**
	 * Constructs a DetailView and set a border and the preferred layout.
	 */
	public DetailView() {
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(layout);
		setBorder(BorderFactory.createEmptyBorder(BORDER, BORDER, BORDER, BORDER));
	}

	/**
	 * Set about which node the DetailView shows some details.
	 * @param node
	 * 		the node to show details about.
	 * @param x
	 * 		x position of the view.
	 * @param y
	 * 		y position of the view.
	 */
	public void setNode(WrapperClone node, int x, int y) {
		removeAll();

		Set<String> genomes = new HashSet<>();
		node.getGenome().forEach(genome -> genomes.add(genome.getIdentifier()));
		addLabels("Genomes:", genomes, "TKK_REF");

		if (node.getLabels().size() > 0) {
			add(" ");
		}

		Set<String> labels = new HashSet<>();
		node.getLabels().forEach(label -> labels.add(label.getText()));
		addLabels("Labels:", labels);

		Dimension size = getPreferredSize();
		setBounds(x, y, size.width, size.height);
	}

	/**
	 * Same as addLabels(String, Set, String) but without a filter.
	 */
	private void addLabels(String title, Set<String> set) {
		addLabels(title, set, null);
	}

	/**
	 * Add JLabels for every object in the given set.
	 * @param title
	 * 		Label to be displayed above the list of labels.
	 * @param set
	 * 		The set of objects to be displayed in the list.
	 * @param filter
	 * 		A string that will always be displayed if found in the set.
	 */
	private void addLabels(String title, Set<String> set, String filter) {
		if (set.size() > 0) {
			add(title);
		}

		Iterator<String> iterator = set.iterator();
		int i = 0;
		while (iterator.hasNext()) {
			String nextString = iterator.next();
			if (filter != null && filter.equals(nextString)) {
				filter = null;
				if (i >= MAX_ROWS) {
					add(nextString);
					i++;
					break;
				}
			}
			if (i < MAX_ROWS) {
				add(nextString);
				i++;
			}
		}
		if (i < set.size()) {
			add("...");
		}
	}

	public Component add(String label) {
		return super.add(new JLabel(label));
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		Container parent = getParent();
		x += POSITION_OFFSET;
		if (parent != null) {
			int parentWidth = parent.getWidth();
			int parentHeight = parent.getHeight();

			width = Math.min(width, parentWidth);
			height = Math.min(height, parentHeight);

			if (x > parentWidth - width) {
				x -= width + 2 * POSITION_OFFSET;
			}
			y = Math.min(y, parentHeight - height);
		}
		super.setBounds(x, y, width, height);
	}
}
