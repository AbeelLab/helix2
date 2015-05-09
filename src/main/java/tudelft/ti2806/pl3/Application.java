package tudelft.ti2806.pl3;

import tudelft.ti2806.pl3.controls.KeyController;
import tudelft.ti2806.pl3.controls.WindowController;
import tudelft.ti2806.pl3.graph.GraphController;
import tudelft.ti2806.pl3.sidebar.SideBarController;
import tudelft.ti2806.pl3.zoomBar.ZoomBarController;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import java.awt.Component;

/**
 * The main application view.
 * <p>
 * <p>
 * Created by Boris Mattijssen on 07-05-15.
 */
public class Application extends JFrame {
	/**
	 * The value of the layers used in the view
	 */
	private static final Integer MIDDEL_LAYER = 50;
	private static final Integer HIGHEST_LAYER = 100;

	private JLayeredPane main;
	private ScreenSize size;

	/**
	 * The controllers of the application
	 */
	private GraphController graphController;
	private SideBarController sideBarController;
	private ZoomBarController zoomBarController;

	/**
	 * Construct the main application view.
	 */
	public Application() {
		super("DNA Bazen");
		// set the size and save it in the singleton
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		main = getLayeredPane();
	}

	/**
	 * Make the window visible and set the sizes
	 */
	public void make() {
		this.setVisible(true);
		size = ScreenSize.getInstance();
		size.setHeight(this.getHeight());
		size.setWidth(this.getWidth());
		size.calculate();
	}

	/**
	 * Get the views and set them to the application
	 * Set the key and window controllers.
	 */
	public void start() {
		addWindowListener(new WindowController(this));
		addKeyListener(new KeyController(this));

		graphController = new GraphController(this);
		zoomBarController = new ZoomBarController(graphController);
		sideBarController = new SideBarController(graphController);

		setSideBarView(sideBarController.getView());
		setGraphView(graphController.getView());
		setZoomBarView(zoomBarController.getView());
	}

	/**
	 * Stop the application and exit
	 */
	public void stop() {
		// save data or do something else here
		this.dispose();
		System.exit(0);
	}

	/**
	 * Asks the user to confirm his choose
	 * @return true if yes, false otherwise
	 */
	public boolean confirm() {
		int answer = JOptionPane.showConfirmDialog(main, "You want to quit?", "Quit",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		return answer == JOptionPane.YES_OPTION;
	}

	/**
	 * Add the sidebar view to the layout
	 * @param view the sidebar view panel
	 */
	public void setSideBarView(Component view) {
		view.setBounds(0, 0, size.getSidebarWidth(), size.getHeight());
		main.add(view, HIGHEST_LAYER);
		view.setVisible(true);
	}

	/**
	 * Add the graph view to the layout.
	 *
	 * @param view the graph view panel
	 */
	public void setGraphView(Component view) {
		view.setBounds(0, 0, size.getWidth(), size.getHeight() - size.getZoombarHeight());
		main.add(view, MIDDEL_LAYER);
		view.setVisible(true);
	}

	/**
	 * Add the zoom bar view to the layout.
	 *
	 * @param view the zoom bar view panel
	 */
	public void setZoomBarView(Component view) {
		view.setBounds(0, size.getHeight() - size.getZoombarHeight(), size.getWidth(), size.getZoombarHeight());
		main.add(view, MIDDEL_LAYER);
		view.setVisible(true);
	}


	public void toggleSideBar(){
		Component sidebar = sideBarController.getView();
		if(sidebar.isVisible()){
			sidebar.setVisible(false);
		}else{
			sidebar.setVisible(true);
		}
	}
}
