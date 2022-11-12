package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
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
	
	JTextField guessResult; 
	JTextField guess; 
	JTextField roll;
	JTextField playerTurn; 
	
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
		addLabel.setText("Whose turn?");
		turnPanel.add(addLabel);
		addText = whoseTurnTextField(); 
		addText.setEditable(false);
		turnPanel.add(addText); 
		return turnPanel;
	}
	
	private JLabel whoseTurnLabel() {
		JLabel turnLabel = new JLabel();
		turnLabel.setHorizontalAlignment(SwingConstants.CENTER);
		return turnLabel;
	}
	
	private JTextField whoseTurnTextField() {
		playerTurn = new JTextField();
		playerTurn.setHorizontalAlignment(SwingConstants.CENTER); 
		return playerTurn;
	}
	
	private JPanel rollPanel() {
		JPanel rollPanel = new JPanel();
		rollPanel.setLayout(new GridLayout(1, 0));
		addLabel = rollLabel(); 
		addLabel.setText("Roll:");
		rollPanel.add(addLabel);
		addText = rollTextField(); 
		addText.setEditable(false);
		rollPanel.add(addText); 
		return rollPanel;
	}
	
	private JLabel rollLabel() {
		JLabel rollLabel = new JLabel();
		rollLabel.setHorizontalAlignment(SwingConstants.CENTER); 
		return rollLabel;
	}
	
	private JTextField rollTextField() {
		roll = new JTextField();
		return roll;
	}
	
	private JButton accusationButton() {
		JButton accusationButton = new JButton();
		addLabel = accusationLabel();  
		addLabel.setText("Make Accusation");
		accusationButton.add(addLabel); 
		return accusationButton;
	}
	
	private JLabel accusationLabel() {
		JLabel accusationLabel = new JLabel(); 
		return accusationLabel; 
	}
	
	private JButton nextButton() {
		JButton nextButton = new JButton();
		addLabel = nextLabel(); 
		addLabel.setText("NEXT!");
		nextButton.add(addLabel); 
		return nextButton;
	}
	
	private JLabel nextLabel() {
		JLabel nextLabel = new JLabel(); 
		return nextLabel; 
	}
	
	private JPanel lowerPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2));
		panel.add(guessPanel());
		panel.add(guessResultPanel());
		return panel;
	}
	
	private JPanel guessPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 0));
		Border blackline = BorderFactory.createTitledBorder("Guess");
		addText = guessTextField();
		addText.setEditable(false);
		panel.add(addText);
		panel.setBorder(blackline);
		return panel;
	}
	
	private JTextField guessTextField() {
		guess = new JTextField();
		return guess;
	}
	
	private JPanel guessResultPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,0));
		Border blackline = BorderFactory.createTitledBorder("Guess Result");
		addText = guessResultTextField();
		addText.setEditable(false);
		panel.add(addText);
		panel.setBorder(blackline);
		return panel;
	}
	
	private JTextField guessResultTextField() {
		guessResult = new JTextField();
		return guessResult;
	}
	
	public void setGuess(String guess) {
		this.guess.setText(guess);
	}
	
	public void SetGuessResult(String guessResult) {
		this.guessResult.setText(guessResult);
	}
	
	public void setRoll(int roll) { 
		this.roll.setText(Integer.toString(roll));
	}
	
	public void setPlayer(String player) {
		this.playerTurn.setText(player);
	}
	
	public void setPlayerColor(Color color) {
		this.playerTurn.setBackground(color);
	}
	
	public void setTurn(Player player, int roll) {
		setRoll(roll); 
		setPlayer(player.getPlayerName()); 
		setPlayerColor(Color.getColor(player.getColor())); 
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
		panel.setTurn(new ComputerPlayer( "Col. Mustard", "orange", 0, 0, "Computer"), 5);
		panel.setGuess("I have no guess!"); 
		panel.SetGuessResult("So you have nothing?");
	}
}
