package com.Assignment3.Utilities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ReadUtility {

	public static String readFromFile(String fileName) {
		StringBuilder builder = new StringBuilder();

		try {
			FileReader filereader = new FileReader(fileName);
			if (filereader == null)
				return new String();
			BufferedReader read = new BufferedReader(filereader);
			String line = null;
			try {
				while ((line = read.readLine()) != null) {
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

	public static HashMap<String, String> populateFileLinkMap(String[] fileList) {
		List<String> fileListNew = Arrays.asList(fileList);
		HashMap<String, String> fileLinkMap = new HashMap<>();

		FileReader filereader = null;
		try {
			filereader = new FileReader("Crawler/ParaLinksMap.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (filereader == null)
			return fileLinkMap;

		BufferedReader read = new BufferedReader(filereader);
		String line = null;
		try {
			while ((line = read.readLine()) != null) {
				String[] tempString = line.split(",");
				if (tempString.length > 1) {
					if (fileListNew.contains(tempString[0])) {
						fileLinkMap.put(tempString[0], tempString[1]);
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fileLinkMap;
	}

}
