package tudelft.ti2806.pl3.menubar;

import tudelft.ti2806.pl3.Application;
import tudelft.ti2806.pl3.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 * Controller for menubar view
 * Created by Kasper on 27-5-2015.
 */
public class MenuBarController implements ActionListener, Controller {

	private Application application;

	/**
	 * Constructs a new controller for {@link MenuBarView}.
	 *
	 * @param application
	 * 		The main application which the action is performed in
	 */
	public MenuBarController(Application application) {
		this.application = application;
	}

	private void stop() {
		application.stop();
	}

	private void readGraphFile() {
		application.makeGraph();
	}

	private void readNwkFile() {
		application.makePhyloTree();
	}

	private void zoomIn() {
		application.getGraphController().zoomLevelUp();
	}

	private void zoomOut() {
		application.getGraphController().zoomLevelDown();
	}

	private void moveLeft() {
		application.getGraphController().moveLeft();
	}

	private void moveRight() {
		application.getGraphController().moveRight();
	}

	private void resetView() {
		application.getGraphController().resetZoom();
	}

	private void displayControls(){
		JOptionPane.showMessageDialog(application,"Controls");
	}
	private void displayAbout(){
		JOptionPane.showMessageDialog(application,"About");
	}

	/**
	 * Controls the Buttons Events from the {@link MenuBarView}.
	 * It reads the button and then starts the correct task.
	 *
	 * @param e
	 * 		is fired when a {@link javax.swing.JMenuItem} is clicked.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			case "Open node and edge file":
				readGraphFile();
				break;
			case "Open .nwk file":
				readNwkFile();
				break;
			case "Exit":
				stop();
				break;
			case "Zoom in ( + )":
				zoomIn();
				break;
			case "Zoom out ( - )":
				zoomOut();
				break;
			case "Move left ( \u2190 )":
				moveLeft();
				break;
			case "Move right ( \u2192 )":
				moveRight();
				break;
			case "Reset view":
				resetView();
				break;
			case "Controls":
				displayControls();
				break;
			case "About Us":
				displayAbout();
				break;
			default:
				break;
		}
	}
}
