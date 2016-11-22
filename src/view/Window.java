package view;

import controller.Controller;
import model.Model;

import javax.swing.*;
import javax.swing.text.JTextComponent;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jdesktop.swingx.autocomplete.ObjectToStringConverter;

import com.sun.imageio.plugins.common.InputStreamAdapter;

import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Window {
	private String[] results = {""};

	private JPanel simpleSearch = new JPanel();
	private JTextComponent searchField;
	private ActionListener listener;
	private ArrayList<String> history = new ArrayList<String>();
	private JList<String> searchResults;
	private JFrame window;

	public Window (ActionListener l){
		listener = l;
	}

	public void createAndShowGUI() {
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
        		AutoCompleteDecorator.decorate(searchField, history, false);
        		
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

	public String getSimpleSearch() {
		return searchField.getText();
	}
	
	public void setHistory(ArrayList<String> hist){
		history = hist; 
	}

	public void updateResults(List<String> newResults){
		searchResults.setListData(newResults.toArray(new String[0]));
		window.repaint();
	}
	
}