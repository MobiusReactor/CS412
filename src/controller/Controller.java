package controller;

import model.Model;
import view.Window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

/**
 * Created by rokas on 16.11.17.
 */
public class Controller implements ActionListener {
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
                	model.updateHistory(gui.getSimpleSearchBox());
                    model.search(gui.getSimpleSearchBox());
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                break;
        }
    }
}
