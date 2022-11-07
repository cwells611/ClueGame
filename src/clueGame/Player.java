package clueGame;

import java.util.ArrayList;
import java.util.Random;

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
}
