package view;

import javax.swing.*;

import model.AdvancedSearch;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdvancedSearchPanel extends JPanel implements ActionListener {
	private JComboBox<String> typeBox;
	private JLabel characterLabel;
	private JTextField searchBox, playBox, characterBox;
	private JButton searchButton;

	public AdvancedSearchPanel(ActionListener listener) {
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
		searchButton.addActionListener(this);
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
				repaint();
			} else {
				remove(characterLabel);
				remove(characterBox);
				repaint();
			}
		} else if (e.getSource() == searchButton) {
			if (typeBox.getSelectedItem().equals("Dialogue")) {
				System.out.println("Searching for \"" + searchBox.getText() + "\" in Dialogue fields of the play \"" + playBox.getText() + "\", spoken by " + characterBox.getText());
			} else {
				System.out.println("Searching for \"" + searchBox.getText() + "\" in " + typeBox.getSelectedItem() + " fields of the play \"" + playBox.getText() + "\"");
			}
			
			AdvancedSearch engine = new AdvancedSearch();
			try {
				engine.search(searchBox.getText(), playBox.getText(), typeBox.getSelectedItem().toString(), characterBox.getText());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
	}
}