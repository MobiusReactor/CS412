package model;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

public class AdvancedSearch {


	public List<Result> search(String userQuery, String play, String searchField, String speakerField) throws Exception {



		String index = "index";
		String field = "contents";
		String playTerm = null;
		String speaker = null;

		if (searchField != null && searchField.trim().length() > 0) {
			if (searchField.equals("Scene Titles")) {
				field = "scene";
				index = "index_scenes";
			} else if (searchField.equals("Stage Directions")) {
				field = "stagedir";
				index = "index_scenes";
			} else if (searchField.equals("All")) {
				field = "contents";
				index = "index_simple";
			}
		}

		if (play != null && play.trim().length() > 0) {
			playTerm = "\"" + play + "\"";
		}

		if (speakerField != null && speakerField.trim().length() > 0 && searchField.equals("Dialogue")) {
			speaker = speakerField;
		}


		IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(index)));
		IndexSearcher searcher = new IndexSearcher(reader);
		Analyzer analyzer = new StandardAnalyzer();

		QueryParser parser = null;

//        if(field.equals("All")){
////			This Parser of all fields are selected
//        parser = new MultiFieldQueryParser(
//                new String[]{"title", "scene", "speaker", "stagedir","contents"},
//                analyzer);
//        } else{

		// This parser if one field is selected
		parser = new QueryParser(field, analyzer);
//        }


		String searchQuery = "(" + field + ":" + userQuery.trim() + ")";

		if (playTerm != null) {
			searchQuery = searchQuery + " AND title:" + playTerm + "";
		}

		if (speaker != null) {
			searchQuery = searchQuery + " AND speaker:" + speaker;
		}

		System.out.println("SearchQ : " + searchQuery);

		Query query = parser.parse(searchQuery);
		// searcher.search(query, 100);

		System.out.println("Searching for: " + query.toString(field));


		return doSearch(searcher, query, searchField);
	}

	public List<Result> doSearch(IndexSearcher searcher, Query query, String type) throws IOException {
		List<Result> resultList = new ArrayList<Result>();
		// Collect enough docs to show 5 pages
		TopDocs results = searcher.search(query, 100);
		ScoreDoc[] hits = results.scoreDocs;

		int numTotalHits = results.totalHits;

		System.out.println("HITS:" + numTotalHits);

		for (int i = 0; i < numTotalHits; i++) {

			Result r = new Result();
			r.setResultType(type);

			Document doc = searcher.doc(hits[i].doc);

			r.setPath(doc.get("path"));
			r.setTitle(doc.get("title"));
			r.setAct(doc.get("act"));
			r.setScene(doc.get("scene"));
			r.setSpeaker(doc.get("speaker"));
			
			System.out.println(doc.get("stagedir"));

			System.out.println((i + 1) + ". " + r.toString());

			resultList.add(r);
		}

		return resultList;
	}
}