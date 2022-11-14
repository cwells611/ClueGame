package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ClueGame extends JFrame {
	//instance variables
	private Board gameBoard; 
	private JFrame game; 
	private DrawBoard gamePanel; 
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
		this.gamePanel = new DrawBoard(); 
		//sets up JFrame behavior such as size, and title 
		this.game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.game.setTitle("Clue");
		this.game.setSize(600, 600);
		//adds the control panel to the bottom of the frame
		this.game.add(controlPanel, BorderLayout.SOUTH); 
		//in the initial creation of the frame, we also want to set the config files 
		//and initialize the board 
		this.gameBoard.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		this.gameBoard.initialize();
		//for initial testing, just adds the known cards panel for the first person 
		//in the board's player list, will change to dynamically update each player 
		this.cardsPanel = new KnownCardsPanel(gameBoard.getPlayers().get(0));
		this.game.add(cardsPanel, BorderLayout.EAST);
		//adds game panel to the center of the JFrame 
		this.game.add(gamePanel, BorderLayout.CENTER); 
		this.game.setVisible(true);
	}

	public static void main(String[] args) {
		//Creates a board instance to be passed into constructor
		Board gameBoard = Board.getInstance();
		//creates an instance of a ClueGame object which is a JFrame
		ClueGame game = new ClueGame(gameBoard); 
	}
}
