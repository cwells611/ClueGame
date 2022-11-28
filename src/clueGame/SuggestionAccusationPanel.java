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
	private JComboBox roomOption;
	private JComboBox personOption;
	private JComboBox weaponOption;
	private JButton submitButton;
	private JButton cancelButton;
	
	
	public SuggestionAccusationPanel(boolean isSuggestion) {
		//setting the title depending on if it is a suggestion or accusation
		if(isSuggestion) {
			setTitle("Make a suggestion");
		}else {
			setTitle("Make an accusation");
		}
		setSize(300,200);
		setLayout(new GridLayout(4,2));
		
		
	}
	
	
}
