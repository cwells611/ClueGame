package clueGame;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class ClueGame extends JFrame {
	//instance variables
	private Board gameBoard; 
	private JFrame game; 
	private GameControlPanel controlPanel; 
	private KnownCardsPanel cardsPanel; 
	
	//constructor that will set all the parts of the JFrame
	//the constructor will be passed a board as a parameter
	public ClueGame(Board board) {
		super(); 
		//sets instance variables
		this.gameBoard = board; 
		this.game = new JFrame(); 
		this.controlPanel = new GameControlPanel(); 
		//for initial testing, the KnownCardsPanel just gets the first player in the 
		//board's array list of players, will change to update with each player's turn
		//this.cardsPanel = new KnownCardsPanel(board.getPlayers().get(0)); 
		//sets up JFrame behavior such as size, and title 
		this.game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.game.setTitle("Clue");
		this.game.setSize(600, 600);
		this.game.setVisible(true);
		//initially adds the control panel and the known cards panel to the JFrame
		this.game.add(controlPanel, BorderLayout.SOUTH); 
		//this.game.add(cardsPanel, BorderLayout.EAST); 
	}

	public static void main(String[] args) {
		//Creates a board instance to be passed into constructor
		Board gameBoard = Board.getInstance();
		//creates an instance of a ClueGame object which is a JFrame
		ClueGame game = new ClueGame(gameBoard); 
	}
}
