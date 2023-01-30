/*
 * @author Sampad Rout
 * (C) Copyright 2020 by Cinch Home Services, Inc.
 */

package com.testautomation.ui.drivers;

import org.openqa.selenium.WebDriver;

public class WebDriverManager {
	
	public static ThreadLocal<WebDriver> webdriverthread = new ThreadLocal<WebDriver>();

	// Get WebDriver Thread
	public synchronized static WebDriver getWebDriverThread() {
		return webdriverthread.get();
	}

	// Set WebDriver Thread
	public synchronized static void setWebDriverThread(WebDriver webdriver) {
		webdriverthread.set(webdriver);
	}

	// Set WebDriver Thread
	public synchronized static void quitWebDriverThread() {
		webdriverthread.remove();
	}
}