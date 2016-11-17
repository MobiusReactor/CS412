package model;

import java.util.Observable;

public class Model extends Observable {
    private SimpleSearch searcher;
    public Model() {
        searcher = new SimpleSearch();
    }

    public void search(String query) throws Exception {
        System.out.println(searcher.search(query));
    }
}
