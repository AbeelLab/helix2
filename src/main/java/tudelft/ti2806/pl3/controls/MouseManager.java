package tudelft.ti2806.pl3.controls;

import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.graphicGraph.GraphicGraph;
import org.graphstream.ui.swingViewer.View;
import org.graphstream.ui.swingViewer.util.DefaultMouseManager;
import tudelft.ti2806.pl3.data.wrapper.WrapperClone;
import tudelft.ti2806.pl3.detailView.DetailView;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * This MouseManager extends the functionality of the default mouse actions of GraphStream.
 * Created by Mathieu Post on 8-6-15.
 */
public class MouseManager extends DefaultMouseManager {
	WrapperClone node = null;
	DetailView detailView;

	public MouseManager() {
		detailView = new DetailView();
	}

	@Override
	public void init(GraphicGraph graph, View view) {
		super.init(graph, view);
		view.setLayout(null);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		int x = e.getX();
		int y = e.getY();
		ArrayList<GraphicElement> graphicElements = view.allNodesOrSpritesIn(x - 5, y - 5, x + 5, y + 5);
		for (GraphicElement graphicElement : graphicElements) {
			System.out.println(graphicElement);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		super.mouseMoved(e);
		int x = e.getX();
		int y = e.getY();
		mouseMoved(x, y);
	}

	public void mouseMoved(int x, int y) {
		ArrayList<GraphicElement> graphicElements = view.allNodesOrSpritesIn(x - 5, y - 5, x + 5, y + 5);
		if (graphicElements.size() == 0) {
			removeDetailView();
		} else {
			GraphicElement element = graphicElements.get(0);
			WrapperClone wrapper = element.getAttribute("node", WrapperClone.class);
			if (node != wrapper) {
				node = wrapper;
				view.add(detailView, BorderLayout.WEST);
				detailView.setNode(node, x + 30, y);
				view.updateUI();
			}
		}
	}

	public void removeDetailView() {
		node = null;
		view.remove(detailView);
		view.updateUI();
	}
}
