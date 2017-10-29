package com.Assignment3.Beans;
import com.Assignment3.Utilities.BasicWebCrawler;
import com.Assignment3.Utilities.Driver;
import com.Assignment3.Utilities.ReadUtility;
import com.Assignment3.Utilities.SimpleLuceneIndexing;

class SingletonBean {
	
	
	private static SingletonBean bean=null;
	static{ 
		{
			bean= new SingletonBean();
			bean.init();
		}
	}
	private void init() {
		
		//Crawl Wiki LInks
		BasicWebCrawler.setParentURL("https://en.wikibooks.org/wiki/Java_Programming");
		Driver.crawlWebContent("https://en.wikibooks.org/wiki/Java_Programming");
		//CrawlOracleLinks
		BasicWebCrawler.setMaxDepth(1);
		BasicWebCrawler.setParentURL("https://docs.oracle.com/javase/tutorial");
		Driver.crawlWebContent("https://docs.oracle.com/javase/tutorial/java/index.html");
		
		SimpleLuceneIndexing.createIndex("Crawler");
		
	}
	public static SingletonBean createAndReturnInstance() {
		return bean;
	}
}
