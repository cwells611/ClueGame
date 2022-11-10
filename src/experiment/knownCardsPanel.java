package experiment;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class knownCardsPanel extends JPanel {

	/**
	 * Constructor for the panel, it does 90% of the work
	 */
	public knownCardsPanel()  {
		//Create a layout with 3 rows
		setLayout(new GridLayout(3,0));
		//need to add people rooms and weapons panels
		
	}

	private JPanel peoplePanel() {
		JPanel panel = new JPanel();
		//needs in hand and seen text labels
		//needs card textFields
		return panel;
	}
	
	private JPanel roomsPanel() {
		JPanel panel = new JPanel();
		//needs in hand and seen text labels
		//needs card textFields
		return panel;
	}
	
	private JPanel weaponsPanel() {
		JPanel panel = new JPanel();
		//needs in hand and seen text labels
		//needs card textFields
		return panel;
	}
	
	private JLabel inHandLabel() {
		JLabel label = new JLabel();
		return label;
	}
	
	private JLabel seenLabel() {
		JLabel label = new JLabel();
		return label;
	}

	private JTextField cardTextField() {
		JTextField textField = new JTextField();
		return textField;
	}
	
	/**
	 * Main to test the panel
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		knownCardsPanel panel = new knownCardsPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(180, 750);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible

		// test filling in the data
		//panel.setTurn(new ComputerPlayer( "Col. Mustard", 0, 0, "orange"), 5);
		//panel.setGuess( "I have no guess!");
		//panel.setGuessResult( "So you have nothing?");
	}
}