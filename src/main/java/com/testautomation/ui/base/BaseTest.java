/*
 * @author Sampad Rout
 * (C) Copyright 2020 by Cinch Home Services, Inc.
 */

package com.testautomation.ui.base;

import com.testautomation.ui.appium.AppiumServer;
import com.testautomation.ui.drivers.AndroidDriverBuilder;
import com.testautomation.ui.drivers.IOSDriverBuilder;
import com.testautomation.ui.drivers.WebDriverBuilder;
import com.testautomation.ui.enums.PlatformName;
import com.testautomation.ui.enums.PlatformType;
import com.testautomation.ui.security.ZapScan;
import com.testautomation.ui.utilities.LoadLogger;
import com.testautomation.ui.drivers.WebDriverManager;

import com.testautomation.ui.visual.ImageComparator;
import org.apache.log4j.Logger;

import org.openqa.selenium.WebDriver;

import org.testng.annotations.*;

import java.io.IOException;
import java.net.MalformedURLException;

public abstract class BaseTest {
	static Logger log = Logger.getLogger(LoadLogger.class);

	protected WebDriver driver;
	public BasePage page;

	public static String axeMode = null;
	public static String capture = null;
	public static String compare = null;
	public static String cloud = null; // this gets value from TestRunner class
	public static String host = null; // this gets value from TestRunner class
	public static boolean headless = false; // this gets value from TestRunner class

	@Parameters({ "platformType", "platformName" })
	@BeforeTest
	public void startAppiumServer(String platformType, @Optional String platformName) throws IOException {
		LoadLogger.getInstance();

		if (platformType.equalsIgnoreCase("mobile")) {
			if (System.getProperty("cloud").equalsIgnoreCase("local")
					&& System.getProperty("os.name").toLowerCase().contains("mac")) {
				killExistingAppiumProcess();
				if (AppiumServer.appium == null || !AppiumServer.appium.isRunning()) {
					AppiumServer.start();
					log.info("Appium server has been started");
				}
			}
		}
	}

	@Parameters({ "platformType", "platformName", "model", "visual", "axe" })
	@BeforeMethod
	public void setupDriver(String platformType, String platformName, @Optional String model, @Optional String visual,
			@Optional String axe) throws IOException {
		LoadLogger.getInstance();

		if (System.getProperty("testType").equalsIgnoreCase("accessibility")) {
			log.info("Accessibility testing starting");

			if (axe.equalsIgnoreCase("critical")) {
				axeMode = "critical";
			} else if (axe.equalsIgnoreCase("moderate")) {
				axeMode = "moderate";
			}
		} else if (System.getProperty("testType").equalsIgnoreCase("visual")) {
			log.info("visual testing starting");

			// set up the MODE and COMPARE for visual tests
			if (visual.equalsIgnoreCase("compare")) {
				ImageComparator.MODE = "visual";
				ImageComparator.COMPARE = true;
				log.info("Running tests in visual compare mode");
			} else if (visual.equalsIgnoreCase("capture")) {
				ImageComparator.MODE = "visual";
				ImageComparator.COMPARE = false;
				log.info("Running tests in visual capture mode");
			}
		} else if (System.getProperty("testType").equalsIgnoreCase("functional")) {
			log.info("Functional tests starting");
		} else if (System.getProperty("testType").equalsIgnoreCase("security")) {
			log.info("Security tests starting");
		}

		if (platformType.equalsIgnoreCase(PlatformType.WEB.toString())) {
			setupWebDriver(platformName);
		} else if (platformType.equalsIgnoreCase(PlatformType.MOBILE.toString())) {
			setupMobileDriver(platformName, model);
		}
	}

	public void setupWebDriver(String platformName) throws MalformedURLException {

		if (System.getProperty("HUB_HOST") != null) {
			host = System.getProperty("HUB_HOST");
		}

		if (System.getProperty("headless") != null) {
			try {
				headless = Boolean.parseBoolean(System.getProperty("headless"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (platformName.equalsIgnoreCase(PlatformName.CHROME.toString())) {
			driver = new WebDriverBuilder().setupDriver(platformName, host, headless);
			WebDriverManager.setWebDriverThread(driver);
			page = new BasePage(WebDriverManager.getWebDriverThread());
		} else if (platformName.equalsIgnoreCase(PlatformName.FIREFOX.toString())) {
			driver = new WebDriverBuilder().setupDriver(platformName, host, headless);
			WebDriverManager.setWebDriverThread(driver);
			page = new BasePage(WebDriverManager.getWebDriverThread());
		} else if (platformName.equalsIgnoreCase(PlatformName.IE.toString())) {
			driver = new WebDriverBuilder().setupDriver(platformName, host, headless);
			WebDriverManager.setWebDriverThread(driver);
			page = new BasePage(WebDriverManager.getWebDriverThread());
		} else if (platformName.equalsIgnoreCase(PlatformName.ZAP_PROXY.toString())) {
			driver = new WebDriverBuilder().setupDriver(platformName, host, headless);
			WebDriverManager.setWebDriverThread(driver);
			page = new BasePage(WebDriverManager.getWebDriverThread());
		}
		log.info(platformName + " driver has been created for execution");
	}

	public void setupMobileDriver(String platformName, String model) throws IOException {
		LoadLogger.getInstance();

		cloud = System.getProperty("cloud");

		if (platformName.equalsIgnoreCase(PlatformName.ANDROID.toString())) {
			driver = new AndroidDriverBuilder().setupDriver(model, cloud);
			WebDriverManager.setWebDriverThread(driver);
			page = new BasePage(WebDriverManager.getWebDriverThread());
		} else if (platformName.equalsIgnoreCase(PlatformName.IOS.toString())) {
			driver = new IOSDriverBuilder().setupDriver(model, cloud);
			WebDriverManager.setWebDriverThread(driver);
			page = new BasePage(WebDriverManager.getWebDriverThread());
		}
		log.info(model + " driver has been created for execution");
	}

	private void killExistingAppiumProcess() throws IOException {
		LoadLogger.getInstance();
		log.info("Killing existing appium process");

		String OSName = System.getProperty("os.name").toLowerCase();
		try {
			if (OSName.contains("mac")) {
				Runtime.getRuntime().exec("killall node");
			} else if (OSName.contains("linux")) {
				Runtime.getRuntime().exec("killall node");
			} else if (OSName.contains("win")) {
				Runtime.getRuntime().exec("taskkill /f /im node.exe");
			}
		} catch (Exception e) {
			log.error("Failed to kill node instance: " + e);
		}
	}

	@AfterMethod
	public void teardownDriver() throws Exception {
		LoadLogger.getInstance();

		try {
			WebDriverManager.getWebDriverThread().quit();
			WebDriverManager.quitWebDriverThread();
			log.info("Driver instance has been quit from execution");
		} catch (Exception e) {
			log.error("Unable to quit driver instance");
		}
	}

	@Parameters({ "platformType", "platformName" })
	@AfterTest
	public void stopAppiumServer(String platformType, @Optional String platformName) throws Exception {
		LoadLogger.getInstance();

		if (System.getProperty("cloud") == null) {
			if (platformType.equalsIgnoreCase(PlatformType.MOBILE.toString())) {
				if (AppiumServer.appium != null || AppiumServer.appium.isRunning()) {
					AppiumServer.stop();
					log.info("Appium server has been stopped");
				}
			}
		}
		if (System.getProperty("testType").equalsIgnoreCase("security")) {
			ZapScan.generateZapReport();
		}
	}

	/*
	 * @AfterTest public void teardown() throws Exception {
	 * LoadLogger.getInstance();
	 * 
	 * try { driver.quit(); log.info("Driver has been quit from execution"); } catch
	 * (Exception e) { log.error("Unable to quit driver"); } }
	 */

}