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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import model.Model;
import model.Result;
import view.Window;

/**
 * Created by rokas on 16.11.17.
 */
public class Controller implements ActionListener, MouseListener {
	private Model model;
	private Window gui;
	private static final String sp = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";

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
			long start = System.nanoTime();

			boolean invalid = false;

			try {

				if (gui.getSearchType().equals("Simple")) {
					results = model.search(gui.getSimpleSearch(), gui.useStem());
				} else {
					results = model.search(gui.getAdvancedSearchTerm(), gui.getAdvancedSearchPlay(),
							gui.getSearchType(), gui.getAdvancedSearchSpeaker());
				}
			} catch (Exception ex) {
				invalid = true;
			}

			if (results.size() > 0 && gui.getSearchType().equals("Simple")) {
				model.updateHistory(gui.getSimpleSearch());
			}

			long time = System.nanoTime() - start;
			if (invalid) {
				gui.showInvalidQuery();
			} else {
				gui.updateResults(results, time);
			}

			break;

		case "comboBoxChanged":
			gui.getSimpleSearchField().setText(gui.getHistoryChoice().getSelectedItem().toString());
			break;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Result r = gui.getSelectedResult();

		// Fix for linux
		String path = r.getPath().replace("\\", File.separator);

		int selected = gui.getSelectedSearch();

		String searchTerms = gui.getAdvancedSearchTerm();

		if (selected == 0) {
			searchTerms = gui.getSimpleSearch();
		}

		String scene = r.getScene();

		File input = new File(path);

		try {
			Document doc = Jsoup.parse(input, "UTF-8", "");
			gui.updateMainPane(formatBody(doc, searchTerms));
			gui.scrollToScene(scene);
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
		body = " <h1> " + play.select("title").first().text() + " </h1><br> ";

		for (Element act : play.select("act")) {
			body = body + " <h2> " + act.select("title").first().text() + " </h2><br> ";

			for (Element scene : act.select("scene")) {
				body = body + " <h3> " + scene.select("title").first().text() + " </h3><br> ";

				for (Element speech : scene.children()) {

					if (speech.tagName().equals("speech")) {
						for (Element line : speech.children()) {
							if (line.tagName().equals("speaker")) {
								body = body + " <b> " + line.text() + " </b><br> ";

							} else if (line.tagName().equals("stagedir")) {
								body = body + " <b><i> " + line.text() + " </i></b><br> ";

							} else {
								if (line.children().size() > 0) {
									for (Element e : line.children()) {
										if (e.tagName().equals("stagedir")) {
											body = body + " <b><i> " + e.text() + " </i></b><br> ";
											body = body + sp + line.text().substring(e.text().length()) + " <br> ";
										}
									}
								} else {
									body = body + sp + line.text() + " <br> ";
								}
							}
						}
					} else if (speech.tagName().equals("stagedir")) {
						body = body + " <b><i> " + speech.text() + " </i></b><br> ";
					}

					body = body + " <br> ";
				}
			}
		}

		int counter = 0;

		if (searchTerms.indexOf('"')>=0) {
			
			ArrayList<String> phrases = new ArrayList<String>();
			
			
			while(searchTerms.indexOf('"')>=0){
				int startIndex = searchTerms.indexOf('"');
				int endIndex = searchTerms.indexOf('"',startIndex + 1);
				String phrase = searchTerms.substring(startIndex+1, endIndex);
				phrases.add(phrase);
				
				searchTerms = searchTerms.substring(0, startIndex) + searchTerms.substring(endIndex+1,searchTerms.length());
			
				searchTerms.trim();

			}

//			searchTerms = searchTerms.substring(1, searchTerms.length() - 1);
			
			StringTokenizer t = new StringTokenizer(searchTerms);
			while (t.hasMoreTokens()) {
				String token = t.nextToken();

				if(token.equals("OR")|| token.equals("AND") || token.equals("NOT")){
					continue;
				}

				
				String outtext = body;
				
				String repfrom = "\\b" + token + "\\b";
				String repto = "<span style=\"background-color:yellow\">" + token.toUpperCase() + "</span>";

				Pattern p = Pattern.compile(repfrom, Pattern.CASE_INSENSITIVE);
				Matcher m = p.matcher(outtext);

				StringBuffer sb = new StringBuffer();
				while (m.find()) {
					counter++;
					m.appendReplacement(sb, repto);
				}
				m.appendTail(sb);

				body = sb.toString();
			}
			
			for(String phrase : phrases){
				
			

			String outtext = body;
			String repfrom = phrase;
			String repto = "<span style=\"background-color:yellow\">" + phrase.toUpperCase() + "</span>";

			Pattern p = Pattern.compile(repfrom, Pattern.LITERAL | Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(outtext);

			StringBuffer sb = new StringBuffer();
			while (m.find()) {
				counter++;
				m.appendReplacement(sb, repto);
			}
			m.appendTail(sb);

			body = sb.toString();
			
			}

			// Update gui with the matches for the document stored in counter
			gui.setTotalDocMatches(counter);

		} else {

			StringTokenizer t = new StringTokenizer(searchTerms);
			while (t.hasMoreTokens()) {
				String token = t.nextToken();
				
				if(token.equals("OR")|| token.equals("AND") || token.equals("NOT")){
					continue;
				}

				String outtext = body;
				String repfrom = "\\b" + token + "\\b";
				String repto = "<span style=\"background-color:yellow\">" + token.toUpperCase() + "</span>";

				Pattern p = Pattern.compile(repfrom, Pattern.CASE_INSENSITIVE);
				Matcher m = p.matcher(outtext);

				StringBuffer sb = new StringBuffer();
				while (m.find()) {
					counter++;
					m.appendReplacement(sb, repto);
				}
				m.appendTail(sb);

				body = sb.toString();

				gui.setTotalDocMatches(counter);

			}
		}

		return body;
	}
}