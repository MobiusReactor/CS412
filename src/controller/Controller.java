package controller;

import model.Model;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import view.Window;

import java.awt.event.*;
import java.io.File;
import java.io.IOException;

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
        switch (e.getActionCommand()) {
            case "Search":
                try {
                	if(model.search(gui.getSimpleSearch()).size() > 0){
                		model.updateHistory(gui.getSimpleSearch());
                	}
                    gui.updateResults(model.search(gui.getSimpleSearch()));
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            case "comboBoxChanged":
            	gui.getSimpleSearchField().setText((String) gui.getHistoryChoice().getSelectedItem());
                break;
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        File input = new File(gui.getSelectedResult());
        try {
            Document doc = Jsoup.parse(input,"UTF-8","");
            gui.updateMainPane(formatBody(doc));
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

    public String formatBody(Document doc) {
        String body = null;
        Element play = doc.select("play").first();
        body = play.select("title").first().text() + "\n";
        for (Element act : play.select("act")) {
            body = body + act.select("title").first().text() + "\n";
            for (Element scene : act.select("scene")) {
                body = body + scene.select("title").first().text() + "\n";
                for (Element speech : scene.select("speech")){
                    body = body + speech.select("speaker").first().text() + "\n \n";
                    for (Element line : speech.select("line")){
                        body = body + line.text() + "\n";
                    }
                    body = body + "\n";
                }
            }
        }
        return body;
    }
}
