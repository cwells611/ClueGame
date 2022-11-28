package clueGame;

import javax.swing.JDialog;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.SwingConstants;

public class SuggestionAccusationPanel extends JDialog {
	private JLabel currentRoomLabel;
	private JLabel personLabel;
	private JLabel weaponLabel;
	//both a combo box and text field exist for the room
		//combo box for an accusation
		//text field for a suggestion
	private JComboBox<String> roomOption;
	private JTextField roomTextField;
	private JComboBox<String> personOption;
	private JComboBox<String> weaponOption;
	private JButton submitButton;
	private JButton cancelButton;
	private ArrayList<String> rooms;
	private ArrayList<String> people;
	private ArrayList<String> weapons;
	private JLabel addLabel;
	
	
	public SuggestionAccusationPanel(boolean isSuggestion) {
		//creating array lists of strings corresponding to each card type
		rooms = new ArrayList<String>();
		people = new ArrayList<String>();
		weapons = new ArrayList<String>();
		//defining all of the cards to be the deck, plus the 3 cards in the solution
		ArrayList<Card> allCards = Board.getInstance().getDeck();
		allCards.add(Board.getInstance().getTheAnswer().getRoom());
		allCards.add(Board.getInstance().getTheAnswer().getPerson());
		allCards.add(Board.getInstance().getTheAnswer().getWeapon());
		//iterating
		for(Card card : allCards) {
			switch(card.getType()) {
			case ROOM:
				rooms.add(card.getName());
				break;
			case PERSON:
				people.add(card.getName());
				break;
			case WEAPON:
				weapons.add(card.getName());
				break;
			}
		}
		
		//setting the title depending on if it is a suggestion or accusation
		if(isSuggestion) {
			setTitle("Make a suggestion");
		}else {
			setTitle("Make an accusation");
		}
		setSize(300,200);
		setLayout(new GridLayout(4,2));
		currentRoomLabel = new JLabel("Current room");
		add(currentRoomLabel);
		if(isSuggestion) {
			roomTextField = new JTextField("TEMP");
			add(roomTextField);
		}else{
			roomOption = createComboBox(rooms);
			add(roomOption);
		}
		personLabel = new JLabel("Person");
		add(personLabel);
		personOption = createComboBox(people);
		add(personOption);
		weaponLabel = new JLabel("Weapon");
		add(weaponLabel);
		weaponOption = createComboBox(weapons);
		add(weaponOption);
		
		
	}
	
	private JComboBox<String> createComboBox(ArrayList<String> options){
		JComboBox<String> combo = new JComboBox<String>();
		for(String option : options) {
			combo.addItem(option);
		}
		return combo;
	}
	
	private JButton submitButton() {
		JButton submitButton = new JButton();
		addLabel = nextLabel(); 
		addLabel.setText("NEXT!");
		submitButton.add(addLabel); 
		return submitButton;
	}
	
}
