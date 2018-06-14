package com;

import java.nio.file.Paths;
import java.util.*;
//import org.apache.lucene.analysis.Analyzer;
//import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
//import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
//import org.apache.lucene.util.BytesRef;

public class SearchTest {
	private Directory dir;
	private IndexReader reader;
	private IndexSearcher is;
	private Set<String> dirList;

	public void setUp() throws Exception {
		// dir = FSDirectory.open(Paths.get("E:\\lucene\\index"));
		dir = FSDirectory.open(Paths.get("C:\\Users\\26404\\eclipse-workspace\\lucene倒排索引\\WebContent\\Index\\"));
		// String direCurr = this.getClass().getResource("").toString();
		// dir = FSDirectory.open(Paths.get(direCurr + "/Index/"));
		reader = DirectoryReader.open(dir);
		is = new IndexSearcher(reader);
		dirList = new HashSet<String>();
	}

	public void tearDown() throws Exception {
		reader.close();
	}

	public TopDocs startSearch(String s) throws Exception {
		String searchField = "content";
		String q = s;
		Term t = new Term(searchField, q);
		Query query = new TermQuery(t);
		TopDocs hits = is.search(query, 100);

		return hits;

	}

	public Set<String> testTermQuery(String s) throws Exception {
		TopDocs hits = startSearch(s);
		for (ScoreDoc scoreDoc : hits.scoreDocs) {
			Document doc = is.doc(scoreDoc.doc);
			// System.out.println(doc.get("fullPath") == null);
			// dirList.add(new String(doc.get("fullpath")));
			String a = doc.get("fullPath").toString();
			dirList.add(a);
			System.out.println(a);
		}
		return dirList;
	}
}