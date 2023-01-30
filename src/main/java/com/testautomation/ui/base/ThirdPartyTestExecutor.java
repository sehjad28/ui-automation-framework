/*
 * @author Sampad Rout
 * (C) Copyright 2020 by Cinch Home Services, Inc.
 */

package com.testautomation.ui.base;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.testautomation.ui.enums.TestResult;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

public abstract class ThirdPartyTestExecutor {
	protected String desiredcapabilitiesfile;
	protected String key;

	public String getDesiredcapabilitiesfile() {
		return desiredcapabilitiesfile;
	}

	public void setDesiredcapabilitiesfile(String desiredcapabilitiesfile) {
		this.desiredcapabilitiesfile = desiredcapabilitiesfile;
	}
	
	protected abstract URL get3rdPartyURL(String username, String password);

	protected abstract String executeTestsOnCBT(String browser, String browserVersion, String platformName, String methodName);
	
	protected abstract void setSessionId(String sessionId);
	
	protected abstract void takeSnapshot();
	
	protected abstract void setResult(TestResult result);

	@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
	public List getDesiredCapabilities(String desiredcapabilitiesfile) throws Exception{
		Gson googleJson = new Gson();
		List desiredcaps = new ArrayList();
		JsonArray desiredcapjsonarray = (JsonArray) new JsonParser().parse(desiredcapabilitiesfile);
		int arraysize = desiredcapjsonarray.size();
		for (int i=0; i<arraysize; i++) {
			desiredcaps.add(desiredcapjsonarray.get(i).toString());
		}
		return desiredcaps;
	};
	

}