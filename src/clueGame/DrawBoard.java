package clueGame;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class DrawBoard extends JPanel {
	//instance variables that will determine the size of each board cell
	int cellWidth; 
	int cellHeight;
	//board instance variable that will allow us to get the size of the board
	//in order to determine the size of each cell 
	Board board; 
	
	//constructor 
	public DrawBoard(Board board) {
		this.board = board; 
		cellWidth = 0; 
		cellHeight = 0; 
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//determine the size of each cell each time paintComponent is called 
		cellWidth = this.getWidth()/this.board.getNumColumns(); 
		cellHeight = this.getHeight()/this.board.getNumRows(); 
		g.setColor(Color.blue);
		g.drawRect(cellWidth, cellHeight, cellWidth, cellHeight); 
	}
	
}
