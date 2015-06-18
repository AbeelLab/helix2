package tudelft.ti2806.pl3.metafilter;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.GridLayout;

/**
 * The view for filtering on metadata
 * Created by Tom Brouws on 16-06-15.
 */
public class MetaFilterView extends JPanel {

	private JComboBox<String> hivStatus;
	private JTextField age;
	private JComboBox<String> gender;
	private JTextField strainLocation;
	private JTextField isolationDate;

	public String getHivStatus() {
		return (String) hivStatus.getSelectedItem();
	}

	public String getAge() {
		return age.getText();
	}

	public String getGender() {
		return (String) gender.getSelectedItem();
	}

	public String getStrainLocation() {
		return strainLocation.getText();
	}

	public String getIsolationDate() {
		return isolationDate.getText();
	}

	/**
	 * Construct the view for the meta filter dialog.
	 */
	public MetaFilterView() {
		super();

		GridLayout gridLayout = new GridLayout(5, 2);
		this.setLayout(gridLayout);

		createHivStatusElements();
		createAgeElements();
		createGenderElements();
		createLocationElements();
		createIsolationDateElements();

		AutoCompleteDecorator.decorate(gender);
	}

	/**
	 * Create the isolation date elements.
	 */
	private void createIsolationDateElements() {
		JLabel isolationDateLabel = new JLabel("Isolation date:");
		isolationDate = new JTextField();
		this.add(isolationDateLabel);
		this.add(isolationDate);
	}

	/**
	 * Create the location elements.
	 */
	private void createLocationElements() {
		JLabel locationLabel = new JLabel("Location:");
		strainLocation = new JTextField();
		this.add(locationLabel);
		this.add(strainLocation);
	}

	/**
	 * Create the gender elements.
	 */
	private void createGenderElements() {
		JLabel genderLabel = new JLabel("Gender:");
		gender = new JComboBox<>(new DefaultComboBoxModel(new String[] {"- None -", "Male", "Female"}));
		this.add(genderLabel);
		this.add(gender);
	}

	/**
	 * Create the age elements.
	 */
	private void createAgeElements() {
		JLabel ageLabel = new JLabel("Age or age range:");
		age = new JTextField();
		this.add(ageLabel);
		this.add(age);
	}

	/**
	 * Create the hiv status elements.
	 */
	private void createHivStatusElements() {
		JLabel hivLabel = new JLabel("HIV status:");
		hivStatus = new JComboBox<>(new DefaultComboBoxModel(new String[] {"- None -", "Positive", "Negative"}));
		this.add(hivLabel);
		this.add(hivStatus);
	}

}
