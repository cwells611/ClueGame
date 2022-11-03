package clueGame;

public class Card {
	private String cardName; 
	private CardType type; 
	
	//constructor 
	public Card(String name, CardType cardType) {
		this.cardName = name;
		this.type = cardType; 
	}
	
	public boolean equals(Card target) {
		if(this.cardName.equals(target.getName())) {
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
