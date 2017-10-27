package com.assignment3.utilities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadUtility {
	
	
	public static ArrayList<String> readFromFilesArray(String [] files) {
		ArrayList<String> result = new ArrayList<String>();
		for(String fileName:files) {
			System.out.println("file is"+fileName);
			String value=readFromFile("Crawler/"+fileName);
			result.add(value);
		}
		
		
		return result;
	}

	private static String readFromFile(String fileName) {
		StringBuilder builder= new StringBuilder();
		try {
			FileReader filereader= new FileReader(fileName);
			if(filereader==null)
				return new String();
			BufferedReader read=new BufferedReader(filereader);
			String line=null;
			try {
				while((line= read.readLine())!=null) {
					builder.append(line);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return builder.toString();
	}

}
