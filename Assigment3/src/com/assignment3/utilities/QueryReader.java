package com.assignment3.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.servlet.ServletContext;

public class QueryReader {

	public static String [] getQueries(ServletContext ctx) {
		ArrayList<String> queries= new ArrayList<>();
		InputStream inStream = ctx.getResourceAsStream("WEB-INF/dataAw.txt");
		String line = null;
		BufferedReader reader= new BufferedReader(new InputStreamReader(inStream));
		StringBuilder responseData = new StringBuilder();
		try {
			while((line = reader.readLine()) != null) {
			  System.out.println(line);
			  queries.add(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return queries.toArray(new String[queries.size()]);
	}
}
