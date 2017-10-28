package com.Assignment3.Beans;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.DisposableBean;

public class ApplicationDisposableBean implements DisposableBean {

	@Override
	public void destroy() throws Exception {
		File f= new File("Crawler");
		if(f.exists() || f!=null) {
		try {
			FileUtils.deleteDirectory(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		}
	}

}
