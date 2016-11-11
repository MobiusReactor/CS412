package gui;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SimpleSearchPanel extends JPanel {
	public SimpleSearchPanel() {
		JLabel l = new JLabel("Enter Search Term");
		add(l);

		JTextField searchBox = new JTextField(15);
		add(searchBox);

		JButton b = new JButton("Search");
		add(b);
	}
}