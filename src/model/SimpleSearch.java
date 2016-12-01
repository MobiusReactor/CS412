package model;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

public class SimpleSearch {


	public List<Result> search(String userQuery, boolean stem) throws Exception {
		String index = "index_simple";
		String field = "contents";
		IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(index)));
		IndexSearcher searcher = new IndexSearcher(reader);
		Analyzer analyzer = null;
		
		if(stem){
			analyzer = new EnglishAnalyzer(CharArraySet.EMPTY_SET);
		}
		else{
			analyzer = new StandardAnalyzer(CharArraySet.EMPTY_SET);
		}

		QueryParser parser = new QueryParser(field, analyzer);


		String searchQuery = userQuery.trim();
		Query query = parser.parse(searchQuery);
		// searcher.search(query, 100);

		System.out.println("Searching for: " + query.toString(field));


		return doSearch(searcher, query);

	}

	public List<Result> doSearch(IndexSearcher searcher, Query query) throws IOException {
		List<Result> result = new ArrayList<Result>();
		// Collect enough docs to show 5 pages
		TopDocs results = searcher.search(query, 100);
		ScoreDoc[] hits = results.scoreDocs;


		int numTotalHits = results.totalHits;

		System.out.println("HITS:" + numTotalHits);

		for (int i = 0; i < numTotalHits; i++) {
			Document doc = searcher.doc(hits[i].doc);

			Result r = new Result();
			r.setResultType("Simple");
			r.setScore(hits[i].score);

			String path = doc.get("path");
			if (path != null) {
				r.setPath(path);
			}
			
			r.setTitle(doc.get("title"));

			result.add(r);
		}
		return result;
	}
}


