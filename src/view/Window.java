package view;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import model.Result;

public class Window {
	private Result[] results = {};

	private JPanel simpleSearch = new JPanel();
	private JTextComponent searchField;
	private ActionListener listener;
	private String[] history = new String[10];
	private ArrayList<String> autoComplete = new ArrayList<String>();
	private JComboBox<String> historyChoice;
	private JList<Result> searchResults;
	private JCheckBox stemCheck;
	private JFrame window;
	private JTextPane document;
	private AdvancedSearchPanel advancedSearch;
	private JTabbedPane searchPanel;
	private JLabel totalResults;
	private JScrollPane resultScrollPane;

	public Window(ActionListener l) {
		listener = l;
	}

	public void createAndShowGUI() {
		setAutoComplete();
		window = new JFrame("Shakespeare Search System");

		ImageIcon image = new ImageIcon("Shakespeare.jpg");
		window.setIconImage(image.getImage());

		window.setBounds(200, 200, 1024, 768);

		window.setLayout(null);


		searchPanel = new JTabbedPane();
		searchPanel.setBounds(35, 10, 200, 190);


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

				stemCheck = new JCheckBox("Use Stemming?");
				simpleSearch.add(stemCheck);

				JButton b = new JButton("Search");
				b.addActionListener(listener);
				simpleSearch.add(b);
			}
		});


		advancedSearch = new AdvancedSearchPanel(listener, this);

		searchPanel.addTab("Simple", simpleSearch);
		searchPanel.addTab("Advanced", advancedSearch);

		window.add(searchPanel);

		totalResults = new JLabel("Total Results: ");
		totalResults.setBounds(135, 215, 130, 20);
		totalResults.setVisible(false);
		window.add(totalResults);

		searchResults = new JList<Result>(results);
		searchResults.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		searchResults.setLayoutOrientation(JList.VERTICAL);
		searchResults.addMouseListener((MouseListener) listener);

		resultScrollPane = new JScrollPane(searchResults);
		resultScrollPane.setBounds(10, 245, 250, 485);
		window.add(resultScrollPane);


		document = new JTextPane();
		document.setContentType("text/html");
		document.setEditable(false);

		JScrollPane documentScrollPane = new JScrollPane(document);
		documentScrollPane.setBounds(270, 10, 740, 720);

		window.add(documentScrollPane);

		window.setVisible(true);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public String getSimpleSearch() {
		return searchField.getText().trim();
	}

	public int getSelectedSearch() {
		return searchPanel.getSelectedIndex();
	}

	public void setHistory(ArrayList<String> hist) {
		if (hist.size() < 10) {
			history = new String[hist.size()];
		}
		Collections.reverse(hist);
		for (int i = 0; i < hist.size() && i < 10; i++) {
			history[i] = hist.get(i);
		}
	}

	public void updateResults(List<Result> results) {
		searchResults.setListData(results.toArray(new Result[results.size()]));
		updateTotalResults();
		window.repaint();
	}

	public String getSelectedResult() {
		return searchResults.getSelectedValue().getPath();
	}

	public void updateMainPane(String text) {
		document.setText(text);
	}

	public JComboBox<String> getHistoryChoice() {
		return historyChoice;
	}

	public JTextField getSimpleSearchField() {
		return (JTextField) searchField;
	}

	public String getSearchType() {
		if (searchPanel.getSelectedIndex() == 0) {
			return "Simple";
		} else {
			return advancedSearch.getType();
		}
	}

	public String getAdvancedSearchTerm() {
		return advancedSearch.getTerm().trim();
	}

	public String getAdvancedSearchPlay() {
		return advancedSearch.getPlay().trim();
	}

	public String getAdvancedSearchSpeaker() {
		return advancedSearch.getSpeaker().trim();
	}

	private void setAutoComplete() {
		autoComplete.add("Hamlet");
		autoComplete.add("Othello");
	}

	public void updateTotalResults() {
		totalResults.setText("Total Results: " + searchResults.getModel().getSize());
		totalResults.setVisible(true);
	}

	public boolean useStem() {
		return stemCheck.isSelected();
	}

	public void setSearchType() {
		if (getSearchType().equals("Dialogue")) {
			searchPanel.setBounds(35, 10, 200, 230);
			totalResults.setBounds(135, 255, 130, 20);
			resultScrollPane.setBounds(10, 285, 250, 445);
		} else {
			searchPanel.setBounds(35, 10, 200, 190);
			totalResults.setBounds(135, 215, 130, 20);
			resultScrollPane.setBounds(10, 245, 250, 485);
		}
	}
}