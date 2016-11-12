package gui;

import search.SimpleSearch;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpleSearchPanel extends JPanel {
	private SimpleSearch searcher = new SimpleSearch();
	private JTextField searchBox;

	public SimpleSearchPanel() {
		JLabel l = new JLabel("Enter Search Term");
		add(l);

		searchBox = new JTextField(15);
		add(searchBox);

		JButton b = new JButton("Search");
		b.addActionListener(new SimpleListener());
		add(b);
	}


	private class SimpleListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			try {
				System.out.println(searcher.search(searchBox.getText()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


}