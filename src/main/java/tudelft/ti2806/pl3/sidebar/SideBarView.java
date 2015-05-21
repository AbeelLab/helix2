package tudelft.ti2806.pl3.sidebar;

import tudelft.ti2806.pl3.ScreenSize;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * The view for the sidebar. The user can select options in the sidebar. Created
 * by Kasper on 7-5-15.
 */
public class SideBarView extends JPanel {
	public static final double SIDEBAR_FACTOR = 0.25;
	
	/**
	 * Constructs the sidebar view with a fixed width.
	 * @param viewList
	 *          the list of all views
	 */
	public SideBarView(ArrayList<Component> viewList) {
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(layout);
		setPreferredSize(new Dimension(ScreenSize.getInstance()
				.getSidebarWidth(), ScreenSize.getInstance().getHeight()));
		setBackground(Color.PINK);
		viewList.forEach(this::add);
	}
}
