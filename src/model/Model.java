package model;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Observable;

public class Model extends Observable {
    private SimpleSearch searcher;
    public Model() {
        searcher = new SimpleSearch();
    }

    public void search(String query) throws Exception {
        System.out.println(searcher.search(query));
    }
    
    public void updateHistory(String newTerm){
    	BufferedWriter bw = null;
		try {
			FileWriter fw = new FileWriter("History.txt", true);
			bw = new BufferedWriter(fw);
			bw.write(newTerm);
			bw.newLine();
		} catch (IOException e) {
			System.err.println("File 'History.txt' not found");
		} finally {
			try {
				bw.close();
			} catch (IOException e) {
				System.err.println("File corrupted");
			}
		}
    }
    
    public String[] readHistory() {
    	String[] history = new String[5];
    	try{
    		FileReader fr = new FileReader("History.txt");
    		BufferedReader br = new BufferedReader(fr);
    		String line = null;
    		int i = 0;
    		while((line = br.readLine()) != null){
    			if(line.length() > 2){
    				history[i] = line;
    				i++;
    			}
    		}
    	} catch(IOException e){
    		e.printStackTrace();
    	}
    	
    	return history;
    }

}
