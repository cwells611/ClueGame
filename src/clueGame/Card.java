package clueGame;

public class Card {
	private String cardName; 
	private CardType type; 
	
	//contructor 
	public Card(String name, CardType cardType) {
		this.cardName = name;
		this.type = cardType; 
	}
	
	public boolean equals(Card target) {
		return false; 
	}
	
	public CardType getType() {
		return this.type; 
	}
}
