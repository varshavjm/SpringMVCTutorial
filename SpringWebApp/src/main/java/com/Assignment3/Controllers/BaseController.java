package com.Assignment3.Controllers;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.Assignment3.Utilities.ReadUtility;
import com.Assignment3.Utilities.SimpleLuceneIndexing;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Controller
public class BaseController {

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {

		model.addAttribute("message", "Spring 3 MVC Hello World");
		return "index";

	}

/*	@RequestMapping(value = "/ajax/{name}", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody String ajax(@PathVariable("name") String name) {
		ArrayList<String> paramList= new ArrayList();
		paramList.add(name);
		paramList.add("Apple");
		paramList.add("Banana");
		ObjectMapper objectMapper = new ObjectMapper();
    	//Set pretty printing of json
    	objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		String arrayToJson = null;
		try {
			arrayToJson = objectMapper.writeValueAsString(paramList);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arrayToJson;

	}
	
	*/
	@RequestMapping(value = "/ajax/{name}", method = RequestMethod.POST, produces="application/json")
	public @ResponseBody synchronized String ajax(@PathVariable("name") String name,@RequestParam("query")String query) {
		ArrayList<String> result= new ArrayList<>();
		
		//Remove spaces and query indices
		query=query.trim();
		query=query.substring(2, query.length());
		System.out.println("....Query is "+query);
		if(query.length()>0) {
		String[] fileList = null;
		try {
			fileList = SimpleLuceneIndexing.getRelevantFilesFromQuery(query);
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		
		for(String file:fileList) {
		result.add(ReadUtility.readFromFile("Crawler/"+file));
		}
		}
		
		//Convert result to json string and return
		ObjectMapper objectMapper = new ObjectMapper();
	  	objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
			String arrayToJson = null;
			try {
				arrayToJson = objectMapper.writeValueAsString(result);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return arrayToJson;
		
	}
	

}