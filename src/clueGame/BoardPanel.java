package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class BoardPanel extends JPanel {
	Board board; 
	int CELL_WIDTH; 
	int CELL_HEIGHT; 
	int xCoord = 0; 
	int yCoord = 0; 
	BoardCell[][] grid; 
	Player human; 
	
	public BoardPanel() {
		board = Board.getInstance();  
		grid = board.getGrid(); 
		this.addMouseListener(new BoardClick());
		human = board.getHumanPlayer(); 
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//determine the size of each cell each time paintComponent is called 
		CELL_WIDTH = this.getWidth()/board.getNumColumns(); 
		CELL_HEIGHT = this.getHeight()/board.getNumRows(); 
		//since the width and height may not be the same and we want squares, 
		//sets the width to be the min of width and height and then sets height to be 
		//that same value 
		CELL_WIDTH = Math.min(CELL_WIDTH, CELL_HEIGHT); 
		CELL_HEIGHT = CELL_WIDTH; 
		//loops through the grid and calls the draw board cell function for each cell
		for(int row = 0; row < board.getNumRows(); row++) {
			for(int col = 0; col < board.getNumColumns(); col++) {
				BoardCell cell = grid[row][col];  
				cell.draw(g, CELL_WIDTH, CELL_HEIGHT, xCoord, yCoord); 
				//after a board cell is draw, we want to increase the xCoord by cellWidth 
				//so the next cell will be drawn right next to it 
				xCoord += CELL_WIDTH; 
			}
			//after an entire row is drawn, we want to increase the yCoord by cellHeight 
			//so the next row will be right under the next and reset the xCoord to 0
			xCoord = 0; 
			yCoord += CELL_HEIGHT; 
		}

		//writing room names 
		for(Room room : board.getRoomMap().values()) {
			if(!room.getName().equals("Walkway") && !room.getName().equals("Unused")) {
				room.draw(g, CELL_WIDTH);	
			}
		}

		//drawing all of the doors
		for(BoardCell door : board.getDoors()) {
			g.setColor(Color.BLUE);
			switch(door.getDoorDirection()) {
			case UP:
				g.fillRect(door.getCol() * CELL_WIDTH, (door.getRow() * CELL_HEIGHT) - 3, CELL_WIDTH, 3);
				break;
			case DOWN:
				g.fillRect(door.getCol() * CELL_WIDTH, ((door.getRow() + 1)  * CELL_HEIGHT), CELL_WIDTH, 3);
				break;
			case LEFT:
				g.fillRect((door.getCol() * CELL_WIDTH) - 3, door.getRow() * CELL_HEIGHT, 3, CELL_HEIGHT);
				break;
			case RIGHT:
				g.fillRect(((door.getCol() + 1) * CELL_WIDTH), door.getRow() * CELL_HEIGHT, 3, CELL_HEIGHT);
				break;
			default:
				break;	
			}
		}

		//drawing the players
		for(Player player : board.getPlayers()) {
			player.draw(g, CELL_WIDTH);
		}
	}

	//class for mouse click 
	private class BoardClick implements MouseListener {
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		@Override
		public void mouseClicked(MouseEvent e) {
			System.out.println("Board clicked");
			//handling mouse click in Board instead of in BoardPanel
			Board.getInstance().processBoardClick(e.getX(), e.getY(), CELL_WIDTH);
		}

	}
	
}
