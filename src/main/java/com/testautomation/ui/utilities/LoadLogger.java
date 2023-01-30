/*
 * @author Vasanth Soundararajan
 * (C) Copyright 2020 by Cinch Home Services, Inc.
 */

package com.testautomation.ui.utilities;

import java.io.InputStream;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LoadLogger{
	static String log4jfile = "log4j.properties"; 
	static String log4jconfigfilepath = "/src/main/resources/configfiles/";
	private static LoadLogger loadlogger = null ; // = new LoadLogger();
	private LoadLogger() {
		try {
			InputStream input = LoadLogger.class.getResourceAsStream("src/main/resources/configfiles/" + log4jfile);
			if (input == null) {
				input= LoadLogger.class.getClassLoader().getResourceAsStream(log4jfile);
				if (input==null){
					input = Thread.currentThread().getContextClassLoader().getResourceAsStream("src/main/resources/configfiles/" + log4jfile);
					if (input==null){
						String log4jfilepath = System.getProperty("user.dir") + OSOperations.getArchSafePath(log4jconfigfilepath) + "log4j.properties";
						System.out.println("Log File:" + log4jfilepath);
						PropertyConfigurator.configure(log4jfilepath);								
					}
					else {
						PropertyConfigurator.configure(input);
					}
				
				}else
				{
					PropertyConfigurator.configure(input);
				}
			
			}
			else {
				PropertyConfigurator.configure(input);
			}			
			
		}catch (Exception ex) {
			System.out.println(ex);
		}	
	}
	public static LoadLogger getInstance() {
		if (loadlogger == null){
			loadlogger = new LoadLogger();
		}
		return loadlogger;
		
	}

	public static void main(String[] args) {
		LoadLogger.getInstance();
		Logger log = Logger.getLogger(LoadLogger.class);
		log.info("LoadLogger Test");
		
	}
}
