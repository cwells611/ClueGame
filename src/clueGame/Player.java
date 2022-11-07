package clueGame;

import java.util.ArrayList;

public abstract class Player {
	private String name;
	private String color; //can also be of type Color, need to decide how we are passing in the color
	//corresponds to player starting location on the board 
	private int row; 
	private int col; 
	private ArrayList<Card> hand;
	private String playerType; 
	private CardType type; 
	
	//constructor 
	public Player(String name, String color, int startRow, int startCol, String type) {
		this.name = name; 
		this.color = color; 
		this.row = startRow; 
		this.col = startCol;  
		this.playerType = type;
		hand = new ArrayList<Card>();
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
	
	public Card disproveSuggestion(Card room, Card person, Card weapon) {
		return null;
	}
}
