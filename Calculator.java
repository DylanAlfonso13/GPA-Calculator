package calculator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

//hi there
class Calculator extends JFrame implements ActionListener {
	private JLabel gradeLabel;
	private JLabel creditLabel;
	private JButton addGrade;
	private JLabel GPADisplay;
	private JTextField creditField;
	private JButton resetButton;
	private JButton saveButton;
	private JButton loadButton;
	private JComboBox<String> dropdown;
	private JLabel errorMessage;

	private double totalPoints = 0.0;
	private double totalCredit = 0.0;
	private int counter = 1;

	private static final DecimalFormat df = new DecimalFormat("0.00");

	public Calculator() {
		//Calculator panel setup
		JPanel panel = new JPanel();
		setTitle("WashU GPA Calculator");
		setSize(400, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		add(panel);
		panel.setLayout(null);

		//Prompt for grade
		gradeLabel = new JLabel("Select grade for class " + (counter));
		gradeLabel.setBounds(0, 20, 245, 25);
		panel.add(gradeLabel);

		//Prompt for credits
		creditLabel = new JLabel("How many credits was class " + (counter) + "?");
		creditLabel.setBounds(0, 50, 245, 25);
		panel.add(creditLabel);

		//Current GPA Display
		GPADisplay = new JLabel("Current GPA: " + 0.0);
		GPADisplay.setBounds(150, 80, 200, 25);
		panel.add(GPADisplay);

		//Choices
		String[] choices = {"A+", "A", "A-", "B+", "B", "B-", 
				"C+", "C", "C-", "D+", "D", "F"};
		dropdown = new JComboBox<String>(choices);
		dropdown.setBounds(250, 20, 100, 25);
		panel.add(dropdown);

		//Credit Field
		creditField = new JTextField(20);
		creditField.setBounds(250, 50, 100, 25);
		panel.add(creditField);

		//Calculate button
		addGrade = new JButton("Enter");
		addGrade.setBounds(17, 110, 80, 25);
		addGrade.addActionListener(this);
		panel.add(addGrade);

		//Save Button
		saveButton = new JButton("Save");
		saveButton.setBounds(105, 110, 80, 25);
		saveButton.addActionListener(this);
		panel.add(saveButton);

		//Load Button
		loadButton = new JButton("Load");
		loadButton.setBounds(192, 110, 80, 25);
		loadButton.addActionListener(this);
		panel.add(loadButton);

		//Reset Button
		resetButton = new JButton("Reset");
		resetButton.setBounds(280, 110, 80, 25);
		resetButton.addActionListener(this);
		panel.add(resetButton);

		//Error Message (Only shows if incorrect data type is thrown, starts blank)
		errorMessage = new JLabel("");
		errorMessage.setBounds(50, 0, 275, 25);
		panel.add(errorMessage);


		setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		boolean creditChecker = true;
		if (e.getSource() == addGrade) {
			String input = (String) dropdown.getSelectedItem();
			double credit = 0.0;
			try {
				credit = Double.parseDouble(creditField.getText());
				if (credit > 0) {
					if(input.equals("A+")) {
						totalPoints += 4.0 * credit;
						totalCredit += credit;
						counter++;
					}
					else if(input.equals("A")) {
						totalPoints += 4.0 * credit;
						totalCredit += credit;
						counter++;
					}
					else if(input.equals("A-")) {
						totalPoints += 3.7 * credit;
						totalCredit += credit;
						counter++;
					}
					else if(input.equals("B+")) {
						totalPoints += 3.3 * credit;
						totalCredit += credit;
						counter++;
					}
					else if(input.equals("B")) {
						totalPoints += 3.0 * credit;
						totalCredit += credit;
						counter++;
					}
					else if(input.equals("B-")) {
						totalPoints += 2.7 * credit;
						totalCredit += credit;
						counter++;
					}
					else if(input.equals("C+")) {
						totalPoints += 2.3 * credit;
						totalCredit += credit;
						counter++;
					}
					else if(input.equals("C")) {
						totalPoints += 2.0 * credit;
						totalCredit += credit;
						counter++;
					}
					else if(input.equals("C-")) {
						totalPoints += 1.7 * credit;
						totalCredit += credit;
						counter++;
					}
					else if(input.equals("D+")) {
						totalPoints += 1.3 * credit;
						totalCredit += credit;
						counter++;
					}
					else if(input.equals("D")) {
						totalPoints += 1.0 * credit;
						totalCredit += credit;
						counter++;
					}
					else if(input.equals("F")) {
						totalCredit += credit;
						counter++;
					}
					if (totalCredit == 0) {
						GPADisplay.setText("Current GPA: " + 0.0);
					}
					else {
						GPADisplay.setText("Current GPA: " + df.format(totalPoints/totalCredit));
					}
					updateLabels();
				}
				else {
					creditChecker = false;
				}
			}
			catch(NumberFormatException ex) {
				creditChecker = false;
			}
		}

		else if (e.getSource() == resetButton) {
			reset();
		}

		else if (e.getSource() == saveButton) {
			save();
		}

		else if (e.getSource() == loadButton) {
			load();
		}
		creditError(creditChecker);
	}

	//Used when resetting panel
	private void updateLabels() {
		gradeLabel.setText("Select grade for class " + (counter));
		creditLabel.setText("How many credits was class " + (counter) + "?");
	}

	//Used for exception error
	private void creditError(boolean creditChecker) {
		if(creditChecker == true) {
			errorMessage.setText("");
		}
		if (creditChecker == false) {
			errorMessage.setText("ERROR: Must use positive number for credits");
		}

	}

	private void save() {
		try {
			FileOutputStream fileOut = new FileOutputStream("calculator.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(this);
			out.close();
			fileOut.close();
		} catch (IOException i) {
			i.printStackTrace();
			errorMessage.setText("Error saving data");
		}
	}


	private void load() {
		try {
			FileInputStream fileIn = new FileInputStream("calculator.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			Calculator savedCalculator = (Calculator) in.readObject();
			in.close();
			fileIn.close();
			totalPoints = savedCalculator.totalPoints;
			totalCredit = savedCalculator.totalCredit;
			counter = savedCalculator.counter;
			GPADisplay.setText("Current GPA: " + df.format(totalPoints/totalCredit));
			updateLabels();

		} catch (IOException i) {
			i.printStackTrace();
			errorMessage.setText("Error loading data");
		} catch (ClassNotFoundException c) {
			c.printStackTrace();
			errorMessage.setText("Error loading data");
		}
	}

	private void reset() {
		totalPoints = 0.0;
		totalCredit = 0.0;
		counter = 1;
		GPADisplay.setText("Current GPA: " + 0.0);
		updateLabels();
	}

}
