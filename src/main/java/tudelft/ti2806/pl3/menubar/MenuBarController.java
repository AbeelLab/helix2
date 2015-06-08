package tudelft.ti2806.pl3.menubar;

import tudelft.ti2806.pl3.Application;
import tudelft.ti2806.pl3.Controller;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

/**
 * Controller for menubar view
 * Created by Kasper on 27-5-2015.
 */
public class MenuBarController implements ActionListener, Controller {

	private Application application;

	/**
	 * Text that is displayed in the About Me option in the Help menu.
	 */
	private final String about = "Helix\u00B2 is a interactive DNA sequence viewer. " +
			"It uses semantic zooming to only display relative information. " +
			"\nThis application was created for as part of a assignment for Contextproject on the TU Delft." +
			"\n" + "\nHelix\u00B2 was created by: " +
			"\n- Tom Brouws" + "\n- Boris Mattijssen" + "\n- Mathieu Post" + "\n- Sam Smulders" + "\n- Kasper Wendel" +
			"\n" + "\nThe code of this application is opensource and can be found on GitHub: " + "\n";

	/**
	 * Text that is displayed in the Controls option in the Help menu.
	 */
	private final String controls = "";

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

	private void displayControls() {
		JOptionPane.showMessageDialog(application, "Tekst", "Controls", JOptionPane.PLAIN_MESSAGE);
	}

	/**
	 * Displays the about me text in a {@link JTextPane}.
	 */
	private void displayAbout() {
		StyleContext styleContext = new StyleContext();
		DefaultStyledDocument doc = new DefaultStyledDocument(styleContext);
		JTextPane textPane = new JTextPane(doc);

		Style webstyle = doc.addStyle("WebStyle", null);
		StyleConstants.setComponent(webstyle, website());
		try {
			doc.insertString(0, about, null);
			doc.insertString(doc.getLength(), "githublink", webstyle);
		} catch (BadLocationException e) {
			// this will not occur since the text is set on correct locations
			e.printStackTrace();
		}
		textPane.setBackground(new Color(240, 240, 240));
		textPane.setEditable(false);
		textPane.setPreferredSize(new Dimension(600, 300));

		JOptionPane.showMessageDialog(application, textPane, "About Me", JOptionPane.PLAIN_MESSAGE);
	}

	/**
	 * Makes a clickable JLabel with the github link of our project.
	 * When the user clicks the label, the browser is opened on our github project.
	 * If there is a error the user will get a message of this.
	 *
	 * @return clickable JLabel with URL
	 */
	private JLabel website() {
		JLabel website = new JLabel("https://github.com/ProgrammingLife3/ProgrammingLife3");
		website.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		website.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop().browse(new URI("https://github.com/ProgrammingLife3/ProgrammingLife3"));
				} catch (IOException | URISyntaxException exception) {
					displayError("A error has occured! We are unable to display the GitHub link in your browser.");
				}
			}
		});
		website.setForeground(new Color(0, 0, 248));
		return website;
	}

	private void displayError(String message) {
		JOptionPane.showMessageDialog(application, message, "Error!", JOptionPane.ERROR_MESSAGE);
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
			case "About Me":
				displayAbout();
				break;
			default:
				break;
		}
	}
}
