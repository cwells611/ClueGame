package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class BoardPanel extends JPanel {
	Board board; 
	int cellWidth = 0; 
	int cellHeight = 0; 
	int xCoord = 0; 
	int yCoord = 0; 
	BoardCell[][] grid; 
	
	public BoardPanel() {
		board = Board.getInstance();  
		grid = board.getGrid(); 
		this.addMouseListener(new BoardClick());
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//determine the size of each cell each time paintComponent is called 
		cellWidth = this.getWidth()/board.getNumColumns(); 
		cellHeight = this.getHeight()/board.getNumRows(); 
		//since the width and height may not be the same and we want squares, 
		//sets the width to be the min of width and height and then sets height to be 
		//that same value 
		cellWidth = Math.min(cellWidth, cellHeight); 
		cellHeight = cellWidth; 
		//loops through the grid and calls the draw board cell function for each cell
		for(int row = 0; row < board.getNumRows(); row++) {
			for(int col = 0; col < board.getNumColumns(); col++) {
				BoardCell cell = grid[row][col];  
				cell.draw(g, cellWidth, cellHeight, xCoord, yCoord); 
				//after a board cell is draw, we want to increase the xCoord by cellWidth 
				//so the next cell will be drawn right next to it 
				xCoord += cellWidth; 
			}
			//after an entire row is drawn, we want to increase the yCoord by cellHeight 
			//so the next row will be right under the next and reset the xCoord to 0
			xCoord = 0; 
			yCoord += cellHeight; 
		}

		//writing room names 
		for(Room room : board.getRoomMap().values()) {
			if(!room.getName().equals("Walkway") && !room.getName().equals("Unused")) {
				room.draw(g, cellWidth);	
			}
		}

		//drawing all of the doors
		for(BoardCell door : board.getDoors()) {
			g.setColor(Color.BLUE);
			switch(door.getDoorDirection()) {
			case UP:
				g.fillRect(door.getCol() * cellWidth, (door.getRow() * cellHeight) - 3, cellWidth, 3);
				break;
			case DOWN:
				g.fillRect(door.getCol() * cellWidth, ((door.getRow() + 1)  * cellHeight), cellWidth, 3);
				break;
			case LEFT:
				g.fillRect((door.getCol() * cellWidth) - 3, door.getRow() * cellHeight, 3, cellHeight);
				break;
			case RIGHT:
				g.fillRect(((door.getCol() + 1) * cellWidth), door.getRow() * cellHeight, 3, cellHeight);
				break;
			default:
				break;	
			}
		}

		//drawing the players
		for(Player player : board.getPlayers()) {
			player.draw(g, cellWidth);
		}
	}

	//class for mouse click 
	private class BoardClick implements MouseListener {
		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}

		@Override
		public void mouseClicked(MouseEvent e) {
			System.out.println("mouse clicked");
		}

	}
}
