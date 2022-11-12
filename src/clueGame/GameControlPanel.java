package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JPanel {
	
	//instance variables that will be reassigned each time a 
	//new GUI feature needs to be added 
	JPanel addPanel; 
	JButton addButton; 
	JTextField addText; 
	JLabel addLabel; 
	
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
		JPanel upperPanel = new JPanel();
		upperPanel.setLayout(new GridLayout(1, 4)); 
		addPanel = whoseTurnPanel(); 
		upperPanel.add(addPanel); 
		addPanel = rollPanel(); 
		upperPanel.add(addPanel);
		addButton = accusationButton(); 
		upperPanel.add(addButton); 
		addButton = nextButton(); 
		upperPanel.add(addButton); 
		return upperPanel; 
	}
	
	private JPanel whoseTurnPanel() {
		JPanel turnPanel = new JPanel();
		turnPanel.setLayout(new GridLayout(2, 1));
		addLabel = whoseTurnLabel(); 
		turnPanel.add(addLabel);
		addText = whoseTurnTextField(); 
		addText.setEditable(false);
		addText.setText("Col. Mustard");
		turnPanel.add(addText); 
		return turnPanel;
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
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2));
		panel.add(guessPanel());
		panel.add(guessResultPanel());
		//TODO needs 2 separate panels
		//guess panel
		//guess result panel
		return panel;
	}
	
	private JPanel guessPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 0));
		Border blackline = BorderFactory.createTitledBorder("Guess");
		addText = guessTextField();
		addText.setEditable(false);
		addText.setText("I have no guess!"); 
		panel.add(addText);
		panel.setBorder(blackline);
		//incorporates guessTextField
		return panel;
	}
	
	private JTextField guessTextField() {
		JTextField guessTextField = new JTextField();
		return guessTextField;
	}
	
	private JPanel guessResultPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,0));
		Border blackline = BorderFactory.createTitledBorder("Guess Result");
		addText = guessResultTextField();
		addText.setEditable(false);
		addText.setText("So you have nothing?"); 
		panel.add(addText);
		panel.setBorder(blackline);
		return panel;
	}
	
	private JTextField guessResultTextField() {
		JTextField guessResultTextField = new JTextField();
		return guessResultTextField;
	}
	
	public void setText(String text) {
		this.addText.setText(text);
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
	}
}
