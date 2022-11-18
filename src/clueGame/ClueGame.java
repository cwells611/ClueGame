package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ClueGame extends JFrame {
	//instance variables
	private GameControlPanel controlPanel; 
	private KnownCardsPanel cardsPanel; 
	private BoardPanel boardPanel; 
	private Board theBoard; 


	//constructor that will set all the parts of the JFrame
	//the constructor will be passed a board as a parameter
	public ClueGame(int width, int height) {
		//sets instance variables
		this.controlPanel = new GameControlPanel();
		this.boardPanel = new BoardPanel(); 
		this.controlPanel = new GameControlPanel(); 
		this.theBoard = Board.getInstance(); 
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
		add(boardPanel, BorderLayout.CENTER); 
		//adds the listener to the button 
		
	}

	public static void main(String[] args) {
		//Creates a board instance to be passed into constructor
		Board gameBoard = Board.getInstance();
		gameBoard.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		gameBoard.initialize();

		//after the board gets initialized, but before we create the frame, set the solution
		Solution solution = gameBoard.getTheAnswer(); 
		//remove solution cards from the deck 
		gameBoard.removeSolutionCards(solution);
		//deal out cards to the players 
		gameBoard.deal();


		//creates an instance of a ClueGame object which is a JFrame
		ClueGame game = new ClueGame(700, 900); 
		game.setVisible(true);

		//Creates Splash Screen 
		JOptionPane.showMessageDialog(game, "You are " + gameBoard.getPlayers().get(0).getPlayerName() + ". \n Can you find the solution \n "
				+ "before the Computer players?", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE); 

		//after the splash screen is displayed, we want to set the turn in the control panel
		game.controlPanel.setTurn(gameBoard.getPlayers().get(gameBoard.getCurrentPlayerIndex()), gameBoard.rollDie());
		//we then want to process the turn 
		gameBoard.processNextTurn();
		
	}
}
; 