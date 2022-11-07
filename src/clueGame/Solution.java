package clueGame;

import java.util.ArrayList;
import java.util.Random;

public class Solution {
	private Card room; 
	private Card person; 
	private Card weapon; 
	private ArrayList<Card> solution = new ArrayList<Card>(); 
	//constructor 
	public Solution() {
		
	}
	
	public void createSolution(ArrayList<Card> deck) {
		//since the deck is formatted where the room get loaded, then the players, then the weapons
		//we can just use different ranges of random numbers to make sure we get the right type of card
		//gets a random room (random number 0-8 since there are 9 rooms)
		Random random = new Random(); 
		int randomRoomIndex = random.nextInt(9);
		this.room = deck.get(randomRoomIndex); 
		int randomPersonIndex = random.nextInt(5) + 10;
		this.person = deck.get(randomPersonIndex); 
		int randomWeaponIndex = random.nextInt(5) + 16; 
		this.weapon = deck.get(randomWeaponIndex); 
		//once all solution cards are generated, add to list 
		solution.add(person); 
		solution.add(weapon);
		solution.add(room);
		//sets the answer in theBoard
		Board.getInstance().setTheAnswer(this);
		
	}
	
	public ArrayList<Card> getSolution() {
		return solution; 
	}
	
	//getters
	public Card getPerson() {
		return this.person;
	}
	
	public Card getRoom() {
		return this.room;
	}
	
	public Card getWeapon() {
		return this.weapon; 
	}
	
	public void setPerson(Card person) {
		this.person = person;
	}
	
	public void setRoom(Card room) {
		this.room = room;
	}
	
	public void setWeapon(Card weapon) {
		this.weapon = weapon;
	}
}
