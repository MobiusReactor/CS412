package main;

import controller.Controller;
import model.Model;
import view.Window;


class Main {

    public static void main(String[] args) {

        Model model = new Model();
        Controller controller = new Controller();
        Window gui = new Window(controller);
        controller.addModel(model);
        controller.addView(gui);
        controller.initializeView();
    }
}
