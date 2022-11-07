package clueGame;

public class Card {
	private String cardName; 
	private CardType type; 
	
	//default constructor 
	public Card() {}
	
	//parameterized constructor
	public Card(String name, CardType cardType) {
		this.cardName = name;
		this.type = cardType; 
	}
	
	public boolean equals(Card target) {
		if(this.cardName.equals(target.getName()) && this.type == target.getType()) {
			return true;
		}
		return false; 
	}
	
	public CardType getType() {
		return this.type; 
	}
	
	public String getName() {
		return this.cardName;
	}
}
