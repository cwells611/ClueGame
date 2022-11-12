package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.SwingConstants;

public class KnownCardsPanel extends JPanel {

	/**
	 * Constructor for the panel, it does 90% of the work
	 */

	JPanel addPanel;
	JTextField addText;

	JPanel peoplePanel;
	JPanel roomsPanel;
	JPanel weaponsPanel;

	private ArrayList<Card> inHandCards;


	public KnownCardsPanel()  {
		//Create a layout with 3 rows
		setLayout(new GridLayout(3,0));
		Border blackline = BorderFactory.createTitledBorder("Known Cards");
		setBorder(blackline);
		peoplePanel = peoplePanel();
		add(peoplePanel);
		roomsPanel = roomsPanel();
		add(roomsPanel);
		weaponsPanel = weaponsPanel();
		add(weaponsPanel);
		//need to add people rooms and weapons panels
	}

	private JPanel peoplePanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 1));
		Border blackline = BorderFactory.createTitledBorder("People");
		panel.setBorder(blackline);
		panel.add(inHandLabel());
		panel.add(seenLabel());
		// needs card textFields
		return panel;
	}

	private JPanel roomsPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 1));
		Border blackline = BorderFactory.createTitledBorder("Rooms");
		panel.setBorder(blackline);
		panel.add(inHandLabel());
		for(Card card : inHandCards) {
			JLabel addLabel = new JLabel();
			addLabel.setText(card.getName());
			panel.add(addLabel);
		}
		panel.add(seenLabel());
		// needs card textFields
		return panel;
	}

	private JPanel weaponsPanel() {
		JPanel panel = new JPanel();
		Border blackline = BorderFactory.createTitledBorder("Weapons");
		panel.setLayout(new GridLayout(0, 1));
		panel.setBorder(blackline);
		panel.add(inHandLabel());
		panel.add(seenLabel());
		// needs card textFields
		return panel;
	}

	private JLabel inHandLabel() {
		JLabel label = new JLabel();
		label.setText("In Hand:");
		return label;
	}

	private JLabel seenLabel() {
		JLabel label = new JLabel();
		label.setText("Seen:");
		return label;
	}

	public void addSeenCard(Player player, Card card) {

		JTextField seenField = new JTextField(); 
		//set the text in the text field to the name of the card 
		seenField.setText(card.getName());
		seenField.setEditable(false);
		switch(card.getType()) {
		//if the card is of type room, then we are going to add the text field to the seen room panel
		case ROOM: 

			break;
		case PERSON:
			break; 
		case WEAPON:
			break; 
		}
	}


	public void addInHandCard(Card card, Player player) {
		JLabel label = new JLabel();
		label.setText(card.getName());
		switch(card.getType()) {
		case ROOM:
			if(player.getHand().contains(card)) {
				inHandCards.add(card);
				//recall the constructor, now that the arrayList is updated
				roomsPanel = roomsPanel();
			}
		case PERSON:
			break;
		case WEAPON:
			break;
		}
	}


	/**
	 * Main to test the panel
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		KnownCardsPanel panel = new KnownCardsPanel(); // create the panel
		JFrame frame = new JFrame(); // create the frame
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(180, 570); // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible


		Player humanPlayer = new HumanPlayer( "Col. Mustard", 0, 0, "orange");

	}
}