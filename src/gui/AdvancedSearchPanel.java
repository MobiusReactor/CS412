package gui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AdvancedSearchPanel extends JPanel {
	public AdvancedSearchPanel() {
		JLabel l = new JLabel("Enter Search Term");
		add(l);

		JTextField searchBox = new JTextField(15);
		add(searchBox);

		JLabel typeLabel = new JLabel("Type");
		add(typeLabel);

		String[] targetType = { "All", "Titles", "Dialogue", "Stage Directions" };
		JComboBox<String> typeBox = new JComboBox<>(targetType);
		add(typeBox);

		JLabel playLabel = new JLabel("Play");
		add(playLabel);

		JTextField playBox = new JTextField(15);
		add(playBox);

		JLabel characterLabel = new JLabel("Character");
		add(characterLabel);

		JTextField characterBox = new JTextField(15);
		add(characterBox);

		JButton b = new JButton("Search");
		add(b);
	}
}
