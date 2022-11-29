package clueGame;

import javax.swing.JDialog;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	private ArrayList<Card> allCards;
	
	
	public SuggestionAccusationPanel(boolean isSuggestion) {
		//creating array lists of strings corresponding to each card type
		rooms = new ArrayList<String>();
		people = new ArrayList<String>();
		weapons = new ArrayList<String>();
		//adding all of the cards from each player's hand, plus the 3 cards in the solution
		allCards = new ArrayList<Card>();
		for(Player player : Board.getInstance().getPlayers()) {
			for( Card card : player.getHand()) {
				allCards.add(card);
			}
		}
		allCards.add(Board.getInstance().getTheAnswer().getRoom());
		allCards.add(Board.getInstance().getTheAnswer().getPerson());
		allCards.add(Board.getInstance().getTheAnswer().getWeapon());
		//iterating through all cards to fill out all possible options
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
		//action listener for combo boxes
		ComboListener comboListener = new ComboListener();
		
		currentRoomLabel = new JLabel("Current room");
		add(currentRoomLabel);
		if(isSuggestion) {
			//getting the name of the room that the human player is in
			Player suggestingPlayer = Board.getInstance().getHumanPlayer();
			BoardCell suggestingRoomCenterCell = Board.getInstance().getGrid()[suggestingPlayer.getRow()][suggestingPlayer.getCol()];
			Room suggestingRoom = Board.getInstance().getRoom(suggestingRoomCenterCell);
			//setting the text field as the room that the suggesting player is in
			roomTextField = new JTextField(suggestingRoom.getName());
			add(roomTextField);
		}else{
			roomOption = createComboBox(rooms);
			roomOption.addActionListener(comboListener);
			add(roomOption);
		}
		personLabel = new JLabel("Person");
		add(personLabel);
		personOption = createComboBox(people);
		personOption.addActionListener(comboListener);
		add(personOption);
		weaponLabel = new JLabel("Weapon");
		add(weaponLabel);
		weaponOption = createComboBox(weapons);
		weaponOption.addActionListener(comboListener);
		add(weaponOption);
		submitButton = button("Submit");
		add(submitButton);
		cancelButton = button("Cancel");
		add(cancelButton);
		
	}
	
	private JComboBox<String> createComboBox(ArrayList<String> options){
		JComboBox<String> combo = new JComboBox<String>();
		for(String option : options) {
			combo.addItem(option);
		}
		return combo;
	}
	
	//default button constructor
	private JButton button(String text) {
		JButton button = new JButton();
		JLabel label = new JLabel();
		label.setText(text);
		button.add(label); 
		return button;
	}
	
	private class ComboListener implements ActionListener {
		  public void actionPerformed(ActionEvent e)
		  {
		    if(e.getSource() == roomOption) {
		    	System.out.println(roomOption.getSelectedItem().toString());
		    }else if(e.getSource() == personOption) {
		    	System.out.println(personOption.getSelectedItem().toString());
		    }else {
		    	System.out.println(weaponOption.getSelectedItem().toString());
		    }
		  }
		}
	
	public static void main(String[] args) {
		Board gameBoard = Board.getInstance();
		gameBoard.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		gameBoard.initialize();

		//after the board gets initialized, but before we create the frame, set the solution
		Solution solution = gameBoard.getTheAnswer(); 
		//remove solution cards from the deck 
		gameBoard.removeSolutionCards(solution);
		//deal out cards to the players 
		gameBoard.deal();
		
		SuggestionAccusationPanel gui = new SuggestionAccusationPanel(false);	
		gui.setVisible(true);
	}
	
}
