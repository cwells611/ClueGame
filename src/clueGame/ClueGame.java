package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ClueGame extends JFrame {
	//instance variables
	private Board gamePanel; 
	private GameControlPanel controlPanel; 
	private KnownCardsPanel cardsPanel; 
	private int currentPlayerIndex; 

	//constructor that will set all the parts of the JFrame
	//the constructor will be passed a board as a parameter
	public ClueGame(int width, int height) {
		//sets instance variables
		this.controlPanel = new GameControlPanel(); 
		this.gamePanel = Board.getInstance();
		this.currentPlayerIndex = 0; 
		//sets up JFrame behavior such as size, and title 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Clue");
		setSize(width, height);
		//before we add the control panel, since the human player will go first, we set the turn 
		//to the human 
		controlPanel.setTurn(Board.getInstance().getPlayers().get(0), Board.getInstance().getPlayers().get(0).rollDie());
		//adds the control panel to the bottom of the frame
		add(controlPanel, BorderLayout.SOUTH); 
		//for initial testing, just adds the known cards panel for the first person 
		//in the board's player list, will change to dynamically update each player 
		this.cardsPanel = new KnownCardsPanel(Board.getInstance().getPlayers().get(0));
		add(cardsPanel, BorderLayout.EAST);
		//adds game panel to the center of the JFrame 
		add(gamePanel, BorderLayout.CENTER); 
		//creates listener object for the next button 
		ButtonListener nextListener = new ButtonListener(); 
		//adds the listener to the button 
		controlPanel.getNext().addActionListener(nextListener);
	}

	//creates class that implements ActionListener to listen to when the button are pressed
	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			//check to see if the next button was pressed 
			if(controlPanel.getNext().isSelected()) {
				//when the next button is first pressed, we want to make sure that 
				//the current human player is finished. To do that we want to make sure
				//they have moved so we will check their row and col position to make sure it 
				//is not the same 
				
				//if the human is finished, update the current player by incrementing 
				//currentPlayerIndex
				
				//have the new player roll the dice 
				
				//calc targets based on the new roll 
				
				//update the control panel with the new player and new roll
				
				//if the current player is human, display the possible targets on the baord 
				
				//flag unfinished, and be done 
				
				//if the current player is a computer, check to see if we can make an accusation
				
				//move
				
				//check to see if we can make a suggestion, and be done
			}

		}

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
		JOptionPane.showMessageDialog(gameBoard, "You are " + gameBoard.getPlayers().get(0).getPlayerName() + ". \n Can you find the solution \n "
				+ "before the Computer players?", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE); 

	}
}
; 