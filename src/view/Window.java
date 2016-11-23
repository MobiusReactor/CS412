package view;

import controller.Controller;
import model.Model;

import javax.swing.*;
import javax.swing.text.JTextComponent;

import org.apache.lucene.util.ArrayUtil;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jdesktop.swingx.autocomplete.ObjectToStringConverter;

import com.sun.imageio.plugins.common.InputStreamAdapter;

import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class Window {
	private String[] results = {""};

	private JPanel simpleSearch = new JPanel();
	private JTextComponent searchField;
	private ActionListener listener;
	private String[] history = new String[10];
	private ArrayList<String> autoComplete = new ArrayList<String>();
	private JComboBox<String> historyChoice;
	private JList<String> searchResults;
	private JFrame window;
	private JTextPane document;

	public Window (ActionListener l){
		listener = l;
	}

	public void createAndShowGUI() {
		setAutoComplete();
		window = new JFrame("Shakespeare Search System");
		
		ImageIcon image = new ImageIcon("Shakespeare.jpg");
		window.setIconImage(image.getImage());
		
		window.setBounds(200, 200, 800, 600);

		window.setLayout(null);


		JTabbedPane searchPanel = new JTabbedPane();
		searchPanel.setBounds(10, 10, 200, 210);


		SwingUtilities.invokeLater(new Runnable() {
            public void run() {

        		JLabel l = new JLabel("Enter Search Term");
        		simpleSearch.add(l);
        		
        		searchField = new JTextField(10);
        		searchField.setEditable(true);
        		simpleSearch.add(searchField);
        		AutoCompleteDecorator.decorate(searchField, autoComplete, false);
        		
        		JLabel historyLabel = new JLabel("Search History: ");
        		simpleSearch.add(historyLabel);
        		
        		historyChoice = new JComboBox<String>(history);
        		historyChoice.setPrototypeDisplayValue("Search History");
        		historyChoice.setSelectedIndex(-1);
        		historyChoice.setMaximumRowCount(10);
        		historyChoice.addActionListener(listener);
        		simpleSearch.add(historyChoice);
        		
        		JButton b = new JButton("Search");
        		b.addActionListener(listener);
        		simpleSearch.add(b);
            }
        });


		JPanel advancedSearch = new AdvancedSearchPanel(listener);

		searchPanel.addTab("Simple", simpleSearch);
		searchPanel.addTab("Advanced", advancedSearch);

		window.add(searchPanel);

		searchResults = new JList<String>(results);
		searchResults.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		searchResults.setLayoutOrientation(JList.VERTICAL);
		searchResults.addMouseListener((MouseListener) listener);

		JScrollPane resultScrollPane = new JScrollPane(searchResults);
		resultScrollPane.setBounds(10, 230, 200, 330);
		window.add(resultScrollPane);


		document = new JTextPane();
		document.setEditable(false);

		JScrollPane documentScrollPane = new JScrollPane(document);
		documentScrollPane.setBounds(220, 10, 570, 550);

		window.add(documentScrollPane);

		window.setVisible(true);
		window.setResizable(false);
	}

	public String getSimpleSearch() {
		return searchField.getText();
	}
	
	public void setHistory(ArrayList<String> hist){
		Collections.reverse(hist);
		for(int i = 0; i < hist.size() && i < 11; i++){
			history[i] = hist.get(i);
		}
	}

	public void updateResults(List<String> newResults){
		searchResults.setListData(newResults.toArray(new String[0]));
		window.repaint();
	}

	public String getSelectedResult() {
		return searchResults.getSelectedValue();
	}

	public void updateMainPane(String text) {
		document.setText(text);
	}
	
	public JComboBox<String> getHistoryChoice(){
		return historyChoice;
	}
	
	public JTextField getSimpleSearchField(){
		return (JTextField) searchField;
	}
	
	private void setAutoComplete(){
		autoComplete.add("Hamlet");
		autoComplete.add("Othello");	
	}
	
}