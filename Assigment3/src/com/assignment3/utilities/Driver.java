package com.assignment3.utilities;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Driver {

	public static void crawlWebContent(String url) {
		//BASIC WEB CRAWLER EXAMPLE	
		BasicWebCrawler crawler= new BasicWebCrawler();
		crawler.getPageLinks(url,0);
		//Write HashMap to file
		HashMap<Integer,String>resultLinks=crawler.getLinksMap();
		writeToFile("./Crawler/index.txt",resultLinks);
		for(Entry entry:resultLinks.entrySet()) {
		writeTextOfLinkToFile(entry);
		}
		
		
		
	}
	
	
	

	private static void writeTextOfLinkToFile(Entry entry) {
		File directory = new File("./Crawler/");
		boolean result=false;
		if(!directory.exists())
			result=directory.mkdir();
		if(result)
			System.out.println("Directory created");
		
		String link= (String) entry.getValue();
		//Extract text from the link and write to the file
			Document doc = null;
			try {
				doc = Jsoup.connect(link).get();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		// Get paragraph elements and create a file for each
			int count=0;
			Elements paragraphs = doc.select("p");
		    for (Element paragraph : paragraphs) {
		     if(paragraph.text().length()<=10)
		    	 		continue;
	     
		    	File f= new File("./Crawler/"+Integer.toString((Integer)entry.getKey())+Integer.toString(count)+".txt");
		    	count++;
				BufferedWriter writer = null;
				try {
					 writer= new BufferedWriter(new FileWriter(f,false));
					 writer.append("\n");
					 writer.append(paragraph.text());
					 writer.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    }
		    }


//Write links to index files
	private static void writeToFile(String fileName, HashMap<Integer, String> resultLinks) {
	
		boolean result=false;
		File directory = new File("./Crawler/");
		if(!directory.exists())
			result=directory.mkdir();
		if(result)
			System.out.println("Directory created");
		
		result=false;
		File f= new File(fileName);
		
		
		if(!f.exists()) {
			try {
				result=f.createNewFile();
				if(result)
					System.out.println("File created");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Writing to file");
		BufferedWriter bw=null;
		FileWriter writer = null;
		
		try {
			writer = new FileWriter(f);
			bw= new BufferedWriter(writer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(Entry entry:resultLinks.entrySet()) {
			try {
				bw.append("\n");
				bw.append(entry.getKey().toString());
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				bw.append(" ");
				bw.append((String)entry.getValue());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
		}
		
		try {
			bw.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


}
