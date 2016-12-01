package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AdvancedSearchPanel extends JPanel implements ActionListener {
	private JComboBox<String> typeBox;
	private JLabel characterLabel;
	private JTextField searchBox, playBox, characterBox;
	private JButton searchButton;
	private Window gui;

	public AdvancedSearchPanel(ActionListener listener, Window gui) {
		this.gui = gui;

		JLabel searchLabel = new JLabel("Enter Search Term");
		add(searchLabel);

		searchBox = new JTextField(15);
		add(searchBox);

		JLabel playLabel = new JLabel("Play");
		add(playLabel);

		playBox = new JTextField(15);
		add(playBox);

		JLabel typeLabel = new JLabel("Type");
		add(typeLabel);

		String[] targetType = { "All", "Scene Titles", "Dialogue", "Stage Directions" };
		typeBox = new JComboBox<>(targetType);
		typeBox.addActionListener(this);
		add(typeBox);

		characterLabel = new JLabel("Character");
		characterBox = new JTextField(15);

		searchButton = new JButton("Search");
		searchButton.addActionListener(listener);
		add(searchButton);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == typeBox) {
			if (typeBox.getSelectedItem().equals("Dialogue")) {
				remove(searchButton);
				add(characterLabel);
				add(characterBox);
				add(searchButton);
				revalidate();
				repaint();
			} else {
				remove(characterLabel);
				remove(characterBox);
				revalidate();
				repaint();
			}
			gui.setSearchType();
		}
	}

	public String getTerm() {
		return searchBox.getText();
	}

	public String getPlay() {
		return playBox.getText();
	}

	public String getType() {
		return typeBox.getSelectedItem().toString();
	}

	public String getSpeaker() {
		return characterBox.getText();
	}
}