package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class knownCardsPanel extends JPanel {

	/**
	 * Constructor for the panel, it does 90% of the work
	 */
	
	JPanel addPanel;
	JTextField addText;
	
	
	public knownCardsPanel()  {
		//Create a layout with 3 rows
		setLayout(new GridLayout(3,0));
		Border blackline = BorderFactory.createTitledBorder("Known Cards");
		setBorder(blackline);
		addPanel = peoplePanel();
		add(addPanel);
		addPanel = roomsPanel();
		add(addPanel);
		addPanel = weaponsPanel();
		add(addPanel);
		//need to add people rooms and weapons panels
		
	}

	private JPanel peoplePanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 1));
		Border blackline = BorderFactory.createTitledBorder("People");
		panel.setBorder(blackline);
		panel.add(inHandLabel());
		panel.add(seenLabel());
		//needs card textFields
		return panel;
	}
	
	private JPanel roomsPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,1));
		Border blackline = BorderFactory.createTitledBorder("Rooms");
		panel.setBorder(blackline);
		panel.add(inHandLabel());
		panel.add(seenLabel());
		//needs card textFields
		return panel;
	}
	
	private JPanel weaponsPanel() {
		JPanel panel = new JPanel();
		Border blackline = BorderFactory.createTitledBorder("Weapons");
		panel.setLayout(new GridLayout(0,1));
		panel.setBorder(blackline);
		panel.add(inHandLabel());
		panel.add(seenLabel());
		//needs card textFields
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

	private JTextField cardTextField() {
		JTextField textField = new JTextField();
		return textField;
	}
	
	/**
	 * Main to test the panel
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		knownCardsPanel panel = new knownCardsPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(180, 570);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible

		
	}
}