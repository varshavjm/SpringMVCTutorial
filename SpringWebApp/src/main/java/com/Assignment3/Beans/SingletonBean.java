package com.Assignment3.Beans;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.queryparser.classic.ParseException;

import com.Assignment3.Utilities.Driver;
import com.Assignment3.Utilities.QueryReader;
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
		
		Driver.crawlWebContent("https://en.wikibooks.org/wiki/Java_Programming");
		SimpleLuceneIndexing.createIndex("Crawler");
		
	}
	public static SingletonBean createAndReturnInstance() {
		return bean;
	}
}
