package view;

import controller.Controller;
import model.Model;

import javax.swing.*;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import java.awt.event.ActionListener;

public class Window {
	private String[] results = { "first result", "second result", "third result" };

	private JPanel simpleSearch = new JPanel();
	private ActionListener listener;
	private JComboBox simpleSearchBox;
	String[] history = new String[100];

	public Window (ActionListener l){
		listener = l;
	}

	public void createAndShowGUI() {
		JFrame window = new JFrame("Shakespeare Search System");
		window.setBounds(200, 200, 800, 600);

		window.setLayout(null);


		JTabbedPane searchPanel = new JTabbedPane();
		searchPanel.setBounds(10, 10, 200, 210);


		SwingUtilities.invokeLater(new Runnable() {
            public void run() {

        		JLabel l = new JLabel("Enter Search Term");
        		simpleSearch.add(l);
        		
        		simpleSearchBox = new JComboBox<String>(history);
                simpleSearchBox.setEditable(true);
        		simpleSearchBox.setSelectedItem(null);
                AutoCompleteDecorator.decorate(simpleSearchBox);
        		simpleSearch.add(simpleSearchBox);
        		
        		JButton b = new JButton("Search");
        		b.addActionListener(listener);
        		simpleSearch.add(b);
            }

        });


		JPanel advancedSearch = new AdvancedSearchPanel(listener);

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

	public String getSimpleSearchBox() {
		return (String) simpleSearchBox.getEditor().getItem();
	}
	
	public void setHistory(String[] hist){
		history = hist;
	}
	
}