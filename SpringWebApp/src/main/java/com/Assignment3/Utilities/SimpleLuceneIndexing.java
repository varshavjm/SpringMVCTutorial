package com.Assignment3.Utilities;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import com.Assignment3.Utilities.MyAnalyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilter;
import org.apache.lucene.analysis.standard.ClassicFilter;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Fields;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.MultiFields;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.Version;
import org.apache.poi.util.SystemOutLogger;

import com.uttesh.exude.ExudeData;

import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.standard.StandardTokenizerImpl;
/**
 * Lucene Demo: basic similarity based content indexing 
 * @author Sharonpova
 * Current sample files fragments of wikibooks and stackoverflow. 
 */


public class SimpleLuceneIndexing {

	private static RAMDirectory ram=null;
	
	private static void indexDirectory(IndexWriter writer, File dir) throws IOException {
		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			File f = files[i];
			if (f.isDirectory()) {
				indexDirectory(writer, f); // recurse
			} else if (f.getName().endsWith(".txt")) {
				// call indexFile to add the title of the txt file to your index (you can also index html)
				indexFile(writer, f);
			}
		}
	}
	private static void indexFile(IndexWriter writer, File f) throws IOException {
		//System.out.println("Indexing " + f.getName());
		Document doc = new Document();
		doc.add(new TextField("filename", f.getName(), TextField.Store.YES));


		//open each file to index the content
		try{

			FileInputStream is = new FileInputStream(f);
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			StringBuffer stringBuffer = new StringBuffer();
			String line = null;
			while((line = reader.readLine())!=null){
				stringBuffer.append(line).append("\n");
			}
			reader.close();
			doc.add(new TextField("contents", stringBuffer.toString(), TextField.Store.YES));


		}catch (Exception e) {

			System.out.println("something wrong with indexing content of the files");
		}    



		writer.addDocument(doc);

	}	

	public static String removeStopWords(String textFile) throws Exception {
		CharArraySet stopWords = EnglishAnalyzer.getDefaultStopSet();
		StandardTokenizer stdToken = new StandardTokenizer();
		stdToken.setReader(new StringReader(textFile.trim()));

		TokenStream tokenStream = new StopFilter (new ASCIIFoldingFilter(new ClassicFilter(new LowerCaseFilter(stdToken))), stopWords);
		StringBuilder sb = new StringBuilder();
		CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
		tokenStream.reset();
		while (tokenStream.incrementToken()) {
			String term = charTermAttribute.toString();
			sb.append(term + " ");
		}
	//	String tempQuery=ExudeData.getInstance().filterStoppings(sb.toString());
	//	return tempQuery;
		return sb.toString();
	}

	public static void createIndex(String folderName) {
		File dataDir = new File(folderName); //my sample file folder path
		// Check whether the directory to be indexed exists
		if (!dataDir.exists() || !dataDir.isDirectory()) {
			try {
				throw new IOException(
						dataDir + " does not exist or is not a directory");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		ram = new RAMDirectory();

		// Specify the analyzer for tokenizing text.
		MyAnalyzer analyzer = new MyAnalyzer();
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		IndexWriter writer = null;
		
		try {
			writer = new IndexWriter(ram, config);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// call indexDirectory to add to your index
		// the names of the txt files in dataDir
		try {
			indexDirectory(writer, dataDir);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	public static String[] getRelevantFilesFromQuery(String query) throws IOException, ParseException {

		
		//Query string! You can change this for text  
		String querystr = null;
		try {
			//TO DO Remove stop words using Exude Library
			querystr = "contents:"+removeStopWords(query);
			//	querystr = "contents:Inheritance and Polymorphism";
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		System.out.println("Qeury with stopwrods remove******\n"+querystr );
		Query q = new QueryParser( "contents", new MyAnalyzer()).parse(querystr);
		int hitsPerPage = 10;

		System.out.println("My query "+q.toString());
		TopScoreDocCollector collector = null;
		IndexSearcher searcher = null;
		if(ram==null)
			System.out.println("Ram is null");
		
		IndexReader reader = DirectoryReader.open(ram);
		//Create TF-IDF
		LinkedHashMap<String,Double> map= createHashMap(q);
		//map=fillTFIDF(map,reader);
		//Create TF-IDF
		//Create new query based on popular keywords from HashMap
		Fields fields = MultiFields.getFields(reader);
		for (String field : fields) {
			Terms terms = fields.terms(field);
			TermsEnum termsEnum = terms.iterator();
            
            BytesRef text;
			while ((text = termsEnum.next()) != null) {
				if(map.keySet().contains(text.utf8ToString())) {
					double tf = 1 + Math.log(termsEnum.totalTermFreq());
					double idf = Math.log(reader.maxDoc()/termsEnum.docFreq());
					map.put(text.utf8ToString(), tf * idf);
				}
			}
		}
		
		Map<String,Double>newMap=sortByValues(map);
		System.out.println(newMap);
	
		//Extract top 10 keywords from the query
		StringBuilder bld= new StringBuilder();
		int index=0;
		bld=bld.append("contents:");
		for(Entry entry:newMap.entrySet()) {
			bld.append(entry.getKey());
			bld.append(" ");
			index++;
			if(index>=5)
				break;
		}
		System.out.println("Query:: "+bld.toString());
		//Create a new Query based on popular keywords
		q = new QueryParser( "contents", new MyAnalyzer()).parse(bld.toString());
		
		searcher = new IndexSearcher(reader);
		collector = TopScoreDocCollector.create(hitsPerPage);
		searcher.search(q, collector);



		ScoreDoc[] hits = collector.topDocs().scoreDocs;
		String [] result= new String[10];
		System.out.println("Found " + hits.length + " hits.");
		for (int i = 0; i < hits.length; ++i) {
			int docId = hits[i].doc;
			Document d;
			d = searcher.doc(docId);

			//System.out.println((i + 1) + ". " + d.get("filename"));
			result[i]=d.get("filename");
		}
		reader.close();
		return result;
	}
	
	
	public static <K,V extends Comparable<V>> Map<K,V> sortByValues(final LinkedHashMap<K,V>map){
        
        Comparator<K>mycomparator= new Comparator<K>(){
            
            public int compare(K k1,K k2){
            		return Double.compare((double) map.get(k2),(double)map.get(k1));
            }
        };
        
        Map<K,V>mymap= new TreeMap(mycomparator);
        mymap.putAll(map);
        return mymap;
    }

		private static LinkedHashMap<String, Double> createHashMap(Query q) {
			LinkedHashMap<String,Double>map= new LinkedHashMap<>();
			String[]str= q.toString().split(" ");
			for(String mystr:str) {
				String [] values= mystr.split(":");
				map.putIfAbsent(values[1], 0.0);

			}
			return map;
		}


	}