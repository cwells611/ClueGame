package clueGame;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class DrawBoard extends JPanel {
	//instance variables that will determine the size of each board cell
	int cellWidth; 
	int cellHeight;
	int xCoord;
	int yCoord; 
	//board instance variable that will allow us to get the size of the board
	//in order to determine the size of each cell 
	Board board; 
	
	//constructor 
	public DrawBoard(Board board) {
		this.board = board; 
		cellWidth = 0; 
		cellHeight = 0; 
		xCoord = 0; 
		yCoord = 0; 
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//determine the size of each cell each time paintComponent is called 
		cellWidth = this.getWidth()/this.board.getNumColumns(); 
		cellHeight = this.getHeight()/this.board.getNumRows(); 
		g.setColor(Color.blue);
		//loops through the grid and calls the draw board cell function for each cell
		for(int row = 0; row < this.board.getNumRows(); row++) {
			for(int col = 0; col < this.board.getNumColumns(); col++) {
				BoardCell cell = this.board.getCell(row, col);  
				cell.drawBoardCell(g, cellWidth, cellHeight, xCoord, yCoord); 
				//after a board cell is draw, we want to increase the xCoord by cellWidth 
				//so the next cell will be drawn right next to it 
				xCoord += cellWidth; 
			}
			//after an entire row is drawn, we want to increase the yCoord by cellHeight 
			//so the next row will be right under the next and reset the xCoord to 0
			xCoord = 0; 
			yCoord += cellHeight; 
		}
	}
	
}
