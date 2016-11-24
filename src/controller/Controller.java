package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import model.AdvancedSearch;
import model.Model;
import model.Result;
import view.Window;

/**
 * Created by rokas on 16.11.17.
 */
public class Controller implements ActionListener, MouseListener {
	private Model model;
	private Window gui;

	public Controller() {
	}

	public void addModel(Model m) {
		model = m;
	}

	public void addView(Window g) {
		gui = g;
	}

	public void initializeView() {
		gui.setHistory(model.readHistory());
		gui.createAndShowGUI();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		List<Result> results = new ArrayList<Result>();
		
		switch (e.getActionCommand()) {
			case "Search":
				try {
					results = model.search(gui.getSimpleSearch());
				} catch (Exception e2) {
					e2.printStackTrace();
				}

				if (results.size() > 0) {
					model.updateHistory(gui.getSimpleSearch());
					gui.updateResults(results);
				}

				break;

			case "Search2":
				try {
					results = model.search(gui.getAdvancedSearchTerm(), gui.getAdvancedSearchPlay(), gui.getAdvancedSearchType(), gui.getAdvancedSearchSpeaker());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				gui.updateResults(results);
				
				break;

			case "comboBoxChanged":
				gui.getSimpleSearchField().setText(gui.getHistoryChoice().getSelectedItem().toString());
				break;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// Fix for linux
		String path = gui.getSelectedResult().replace("\\", File.separator);

		String searchTerms = gui.getSimpleSearch();

		File input = new File(path);

		try {
			Document doc = Jsoup.parse(input, "UTF-8", "");
			gui.updateMainPane(formatBody(doc, searchTerms));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	public String formatBody(Document doc, String searchTerms) {
		String body = null;
		Element play = doc.select("play").first();
		body = "<h1>" + play.select("title").first().text() + "</h1><br>";
		for (Element act : play.select("act")) {
			body = body + "<h2>" + act.select("title").first().text() + "</h2><br>";
			for (Element scene : act.select("scene")) {
				body = body + "<h3>" + scene.select("title").first().text() + "</h3><br>";
				for (Element speech : scene.select("speech")) {
					body = body + "<h4>" + speech.select("speaker").first().text() + "</h4>";
					for (Element line : speech.select("line")) {
						body = body + line.text() + "<br>";
					}
					body = body + "<br>";
				}
			}
		}

		StringTokenizer t = new StringTokenizer(searchTerms);
		while (t.hasMoreTokens()) {
			String token = t.nextToken();
			body = body.replaceAll("(?i)" + token, "<span style=\"background-color:yellow\">" + token.toUpperCase() + "</span>");
		}

		return body;
	}
}