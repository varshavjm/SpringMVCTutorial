package com.assignment3.utilities;
import java.io.*;
import java.util.HashMap;
import java.util.HashSet;

import org.jsoup.select.Elements;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class BasicWebCrawler {
	private HashMap<Integer,String> links;
	private static final int MAX_DEPTH=1;
	private static int count=0;
    public BasicWebCrawler() {
        links = new HashMap<Integer,String>();
   
    }
    
    public HashMap<Integer,String> getLinksMap(){
    	return links;
    }

    public void getPageLinks(String URL, int depth) {
        //4. Check if you have already crawled the URLs
        if (!links.containsValue(URL) && depth<=MAX_DEPTH) {
            try {
                //4. (i) If not add it to the index
            		if(!URL.contains("https://en.wikibooks.org/wiki/Java_Programming"))
            				return;
            		
                if (links.put(count++,URL) == null) {
                //    System.out.println("Cnt"+count+"Depth->"+depth+" URL->"+URL);
                    
                }

                //2. Fetch the HTML code
                Document document = Jsoup.connect(URL).get();
                //3. Parse the HTML to extract links to other URLs
                Elements linksOnPage = document.select("a[href]");
                	depth++;
                //5. For each extracted URL... go back to Step 4.
                for (Element page : linksOnPage) {
                	
    
                    getPageLinks(page.attr("abs:href"),depth);
                }
            } catch (IOException e) {
                System.err.println("For '" + URL + "': " + e.getMessage());
            }
        }
}

}