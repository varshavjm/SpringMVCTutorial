package com.assignment3.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.queryparser.classic.ParseException;

import com.assignment3.utilities.Driver;
import com.assignment3.utilities.QueryReader;
import com.assignment3.utilities.ReadUtility;
import com.assignment3.utilities.SimpleLuceneIndexing;

import jdk.nashorn.internal.ir.RuntimeNode.Request;

/**
 * Servlet implementation class getIndexedContent
 */
@WebServlet("/getIndexedContentServlet")
public class getIndexedContentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static ArrayList<ArrayList<String>> result=null;
	public static String[] queries=null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getIndexedContentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setAttribute("queries", queries);
		request.setAttribute("result", result);
		request.getRequestDispatcher("index.jsp").include(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	   }

	
	
	@Override
	   public void init(ServletConfig sc) {
	       // load properties from disk, do be used by subsequent doGet() calls
		System.out.println("Inside");
		Driver.crawlWebContent("https://en.wikibooks.org/wiki/Java_Programming");
		SimpleLuceneIndexing.createIndex("Crawler");
		queries= QueryReader.getQueries(sc.getServletContext());
		result= new ArrayList<>();
		for(String query:queries)
			try {
				String [] files=SimpleLuceneIndexing.getRelevantFilesFromQuery(query);
				if(files.length>0)
				result.add(ReadUtility.readFromFilesArray(files));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
}
