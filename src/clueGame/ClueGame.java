package clueGame;

import javax.swing.JFrame;

public class ClueGame extends JFrame {
	//instance variables
	private Board gameBoard; 
	private JFrame game; 
	//constructor that will set all the parts of the JFrame
	//the constructor will be passed a board as a parameter
	public ClueGame(Board board) {
		super(); 
		this.gameBoard = board; 
		this.game = new JFrame(); 
		this.game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.game.setTitle("Clue");
		this.game.setSize(400, 400);
		this.game.setVisible(true);
	}

	public static void main(String[] args) {
		//Creates a board instance to be passed into constructor
		Board gameBoard = Board.getInstance(); 		//creates an instance of a ClueGame object which is a JFrame
		ClueGame game = new ClueGame(gameBoard); 
	}
}
