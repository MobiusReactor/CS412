package model;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SimpleSearch {


    public List<String> search(String userQuery) throws Exception {
        String index = "index_simple";
        String field = "contents";
        IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(index)));
        IndexSearcher searcher = new IndexSearcher(reader);
        Analyzer analyzer = new StandardAnalyzer();

        QueryParser parser = new QueryParser(field, analyzer);
        
 
        String searchQuery = userQuery.trim();
        Query query = parser.parse(searchQuery);
      //  searcher.search(query, 100);
        
        System.out.println("Searching for: " + query.toString(field));


        return doSearch(searcher,query);
   
    }



    public List<String> doSearch(IndexSearcher searcher, Query query) throws IOException {
        List<String> result = new ArrayList<String>();
        // Collect enough docs to show 5 pages
        TopDocs results = searcher.search(query, 100);
        ScoreDoc[] hits = results.scoreDocs;
        
        

        int numTotalHits = results.totalHits;
        
        System.out.println("HITS:" + numTotalHits);

        for (int i = 0; i < numTotalHits; i++) {
            Document doc = searcher.doc(hits[i].doc);
            String path = doc.get("path");
            if (path != null) {
                result.add(path);
            }
        }
        return result;
    }
}


