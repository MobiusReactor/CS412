package controller;

import model.Model;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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
                	System.out.println(gui.getSimpleSearch());
                	model.updateHistory(gui.getSimpleSearch());
                    gui.updateResults(model.search(gui.getSimpleSearch()));
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                break;
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        File input = new File(gui.getSelectedResult());
        try {
            Document doc = Jsoup.parse(input,"UTF-8","");
            gui.updateMainPane(doc.body().toString());
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
}
