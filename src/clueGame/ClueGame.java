package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ClueGame extends JFrame {
	//instance variables
	private Board gameBoard;  
	private Board gamePanel; 
	private GameControlPanel controlPanel; 
	private KnownCardsPanel cardsPanel; 
	
	//constructor that will set all the parts of the JFrame
	//the constructor will be passed a board as a parameter
	public ClueGame(int width, int height) {
		//sets instance variables
		this.controlPanel = new GameControlPanel(); 
		this.gamePanel = Board.getInstance(); 
		//sets up JFrame behavior such as size, and title 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Clue");
		setSize(width, height);
		//adds the control panel to the bottom of the frame
		add(controlPanel, BorderLayout.SOUTH); 
		//for initial testing, just adds the known cards panel for the first person 
		//in the board's player list, will change to dynamically update each player 
		this.cardsPanel = new KnownCardsPanel(Board.getInstance().getPlayers().get(0));
		add(cardsPanel, BorderLayout.EAST);
		//adds game panel to the center of the JFrame 
		add(gamePanel, BorderLayout.CENTER); 
	}

	public static void main(String[] args) {
		//Creates a board instance to be passed into constructor
		Board gameBoard = Board.getInstance();
		gameBoard.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		gameBoard.initialize();
		//creates an instance of a ClueGame object which is a JFrame
		ClueGame game = new ClueGame(700, 900); 
		game.setVisible(true);
	}
}
