package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public abstract class Player {
	private String name;
	private Color color; 
	//corresponds to player starting location on the board 
	private int row; 
	private int col; 
	private ArrayList<Card> hand;
	private String playerType; 
	private CardType type; 
	private ArrayList<Card> seenCards;
	private int playerRadius = 0; 
	private boolean computerReady = false;  
	private boolean canSuggest = false;


	//constructor 
	public Player(String name, String color, int startRow, int startCol) {
		this.name = name; 
		this.row = startRow; 
		this.col = startCol;
		try {
			Field field = Class.forName("java.awt.Color").getField(color.toUpperCase()); 
			this.color = (Color)field.get(null);
		} catch (Exception e) {
			System.out.println("Invalid color");
			this.color = null; 
		}
		hand = new ArrayList<Card>();
		seenCards = new ArrayList<Card>(); 
		for(Card card : hand) {
			seenCards.add(card);
		}
	}

	public Card disproveSuggestion(Card room, Card person, Card weapon) {
		//creating an arraylist of the cards that match
		ArrayList<Card> matchingCards = new ArrayList<Card>();
		//checking if each of the 3 cards in the suggestion are in the player's hand
		if(hand.contains(room)) {
			matchingCards.add(room);
		}
		if(hand.contains(person)) {
			matchingCards.add(person);
		}
		if(hand.contains(weapon)) {
			matchingCards.add(weapon);
		}
		//return the only matching card if there is only one
		if(matchingCards.size() == 1) {
			return matchingCards.get(0);
		}
		//picking a random card from the hand if there is more than one matching card
		else if(matchingCards.size() > 1) {
			Random random = new Random();
			int randInt = random.nextInt(matchingCards.size());
			return matchingCards.get(randInt);
		}
		//returning null if no matching cards
		return null;
	}



	public void draw(Graphics g, int diameter) {
		playerRadius = diameter/2; 
		//check to see if the player is being drawn in a room 
		if(Board.getInstance().getCell(row, col).getIsRoom()) {
			//loop through the players 
			for(Player player: Board.getInstance().getPlayers()) {
				if(player == this) {
					continue; 
				}
				//check of any of the other players are at the same location 
				else if(this.row == player.row && this.col == player.col) { 
					g.setColor(player.color); 
					if(Board.getInstance().getRoom(Board.getInstance().getCell(row, col)).getCharacterCounter() > 2) {
						playerRadius += (playerRadius/2); 
					}
					g.fillOval((diameter * col) + playerRadius, (diameter * row) + 1, diameter - 2, diameter - 2);
					g.setColor(Color.BLACK);
					g.drawOval((diameter * col) + playerRadius, (diameter * row) + 1, diameter - 2, diameter - 2);
				}
				else {
					g.setColor(this.color); 
					g.fillOval((diameter * col) + 1, (diameter * row) + 1, diameter - 2, diameter - 2);
					g.setColor(Color.BLACK);
					g.drawOval((diameter * col) + 1, (diameter * row) + 1, diameter - 2, diameter - 2);
				}
			}
		}
		else {
			//setting the color
			g.setColor(this.color); 
			g.fillOval((diameter * col) + 1, (diameter * row) + 1, diameter - 2, diameter - 2);
			g.setColor(Color.BLACK);
			g.drawOval((diameter * col) + 1, (diameter * row) + 1, diameter - 2, diameter - 2);
		}
	}

	//Getters and Setters 
	public ArrayList<Card> getSeenCards() {
		return seenCards;
	}

	public void addSeenCard(Card card) {
		this.seenCards.add(card);
	}

	public void setCardType(CardType cardType) {
		this.type = cardType; 
	}
	public CardType getCardType() {
		return this.type; 
	}

	public String getPlayerType() {
		return this.playerType;
	}

	public String getPlayerName() {
		return this.name; 
	}

	public ArrayList<Card> getHand(){
		return this.hand;
	}

	public void updateHand(Card card) {
		hand.add(card);
	}
	public Color getColor() {
		return this.color; 
	}
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public boolean canSuggest() {
		return canSuggest;
	}

	public void setCanSuggest(boolean canSuggest) {
		this.canSuggest = canSuggest;
	}
	public BoardCell selectTarget(Set<BoardCell> targets, Board board) {
		return null;
	}

	public Solution createSuggestion(Board board, Room currentRoom) {
		return null; 
	}

	public boolean isComputerReady() {
		return computerReady;
	}

	public void setComputerReady(boolean computerReady) {
		this.computerReady = computerReady;
	}
	public Solution doAccusation() {
		return null;
	}
}
