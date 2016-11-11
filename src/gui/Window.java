package gui;

import javax.swing.*;

public class Window {
	private static String[] results = { "first result", "second result", "third result" };

	public static void main(String[] args) {
		JFrame window = new JFrame("Shakespeare Search System");
		window.setBounds(200, 200, 800, 600);

		window.setLayout(null);


		JTabbedPane searchPanel = new JTabbedPane();
		searchPanel.setBounds(10, 10, 200, 210);


		JPanel simpleSearch = createSimpleSearchPanel();
		JPanel advancedSearch = createAdvancedSearchPanel();

		searchPanel.addTab("Simple", simpleSearch);
		searchPanel.addTab("Advanced", advancedSearch);

		window.add(searchPanel);

		JList<String> searchResults = new JList<String>(results);
		searchResults.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		searchResults.setLayoutOrientation(JList.VERTICAL);

		JScrollPane resultScrollPane = new JScrollPane(searchResults);
		resultScrollPane.setBounds(10, 230, 200, 330);
		window.add(resultScrollPane);


		JTextPane document = new JTextPane();
//		document.setEditable(false);

		JScrollPane documentScrollPane = new JScrollPane(document);
		documentScrollPane.setBounds(220, 10, 570, 550);

		window.add(documentScrollPane);

		window.setVisible(true);
		window.setResizable(false);
	}

	public static JPanel createSimpleSearchPanel() {
		JPanel panel = new JPanel();

		JLabel l = new JLabel("Enter Search Term");
		panel.add(l);

		JTextField searchBox = new JTextField(15);
		panel.add(searchBox);

		JButton b = new JButton("Search");
		panel.add(b);

		return panel;
	}

	public static JPanel createAdvancedSearchPanel() {
		JPanel panel = new JPanel();

		JLabel l = new JLabel("Enter Search Term");
		panel.add(l);

		JTextField searchBox = new JTextField(15);
		panel.add(searchBox);

		JLabel typeLabel = new JLabel("Type");
		panel.add(typeLabel);
		
		String[] targetType = { "All", "Titles", "Dialogue", "Stage Directions" };
		JComboBox<String> typeBox = new JComboBox<>(targetType);
		panel.add(typeBox);

		JLabel playLabel = new JLabel("Play");
		panel.add(playLabel);

		JTextField playBox = new JTextField(15);
		panel.add(playBox);

		JLabel characterLabel = new JLabel("Character");
		panel.add(characterLabel);

		JTextField characterBox = new JTextField(15);
		panel.add(characterBox);

		JButton b = new JButton("Search");
		panel.add(b);

		return panel;
	}
}