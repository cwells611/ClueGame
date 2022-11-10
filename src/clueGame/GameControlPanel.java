package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JPanel {

	
	/**
	 * Constructor for the panel, it does 90% of the work
	 */
	public GameControlPanel()  {
		//Create a layout with 2 rows
		setLayout(new GridLayout(2,0));
		JPanel panel = upperPanel();
		add(panel);
		panel = lowerPanel();
		add(panel);
	}

	
	private JPanel upperPanel() {
		JPanel panel = new JPanel();
		
		//TODO needs 4 separate panels
		//whose turn panel
		//roll panel
		//make accusation button
		//next button
		return panel;
	}
	
	private JPanel whoseTurnPanel() {
		JPanel panel = new JPanel();
		//incorporates whoseTurnLabel and whoseTurnTextField
		return panel;
	}
	
	private JLabel whoseTurnLabel() {
		JLabel label = new JLabel();
		return label;
	}
	
	private JTextField whoseTurnTextField() {
		JTextField textField = new JTextField();
		return textField;
	}
	
	private JPanel rollPanel() {
		JPanel panel = new JPanel();
		//incorporates rollLabel and rollTextField
		return panel;
	}
	
	private JLabel rollLabel() {
		JLabel label = new JLabel();
		return label;
	}
	
	private JTextField rollTextField() {
		JTextField textField = new JTextField();
		return textField;
	}
	
	private JButton accusationButton() {
		JButton button = new JButton();
		return button;
	}
	
	private JButton nextButton() {
		JButton button = new JButton();
		return button;
	}
	
	private JPanel lowerPanel() {
		//TODO needs 2 separate panels
		JPanel panel = new JPanel();
		setLayout(new GridLayout(0, 2));
		addPanel = guessPanel();
		panel.add(addPanel);
		addPanel = guessResultPanel();
		panel.add(addPanel);
		//guess panel
		//guess result panel
		return panel;
	}
	
	private JPanel guessPanel() {
		JPanel panel = new JPanel();
		setLayout(new GridLayout(1, 0));
		addTextField = guessTextField();
		
		//incorporates guessTextField
		return panel;
	}
	
	private JTextField guessTextField() {
		JTextField textField = new JTextField();
		
		return textField;
	}
	
	private JPanel guessResultPanel() {
		JPanel panel = new JPanel();
		return panel;
	}
	
	private JTextField guessResultTextField() {
		JTextField textField = new JTextField();
		return textField;
	}
	

	/**
	 * Main to test the panel
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible

		// test filling in the data
		//panel.setTurn(new ComputerPlayer( "Col. Mustard", 0, 0, "orange"), 5);
		//panel.setGuess( "I have no guess!");
		//panel.setGuessResult( "So you have nothing?");
	}
}
