package model;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Model extends Observable {
    private SimpleSearch searcher;
    private AdvancedSearch advancedSearcher;
    
    public Model() {
        searcher = new SimpleSearch();
        advancedSearcher = new AdvancedSearch();
    }

    public List<String> search(String query) throws Exception {
        return searcher.search(query);
    }
    
    public void advancedSearch(String term, String play, String field, String speaker) throws Exception{
    	//System.out.println(advancedSearcher.search(term,play,field,speaker));
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
    
    public ArrayList<String> readHistory() {
    	ArrayList<String> historyList = new ArrayList<String>();
    	try{
    		FileReader fr = new FileReader("History.txt");
    		BufferedReader br = new BufferedReader(fr);
    		
    		String line = null;
    		while((line = br.readLine()) != null){
    			if(line.length() > 2 && !historyList.contains(line)){
    				historyList.add(line);
    			}
    		}
    		
    		br.close();
    	} catch(IOException e){
    		e.printStackTrace();
    	}
    	return historyList;
    }

}
