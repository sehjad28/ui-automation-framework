/*
 * @author Sampad Rout
 *
 */

package com.testautomation.ui.drivers;

import com.testautomation.ui.config.DeviceConfig;
import com.testautomation.ui.enums.PlatformName;
import com.testautomation.ui.utilities.LoadLogger;

import com.testautomation.ui.security.ZapScan;
import io.github.bonigarcia.wdm.WebDriverManager;

import org.apache.log4j.Logger;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.concurrent.TimeUnit;

import com.testautomation.ui.security.*;

public class WebDriverBuilder extends DeviceConfig {

	public WebDriver driver;
	/*
	 * ChromeOptions class in Java also implements the Capabilities interface,
	 * allowing you to specify other WebDriver capabilities not specific to
	 * ChromeDriver. Use capabilities.merge() to merge chrome and non-chrome
	 * specific caps.
	 */
	public Capabilities capabilities;

	static Logger log = Logger.getLogger(LoadLogger.class);

	public WebDriver setupDriver(String platformName, String host, Boolean headless) throws MalformedURLException {
		LoadLogger.getInstance();

		if (platformName.equalsIgnoreCase(PlatformName.CHROME.name())) {
			if (host != null) {
				String completeUrl = "http://" + host + ":4444/wd/hub";

				if (headless) {
					capabilities = OptionsManager.getHeadlessChromeOptions();
					driver = new RemoteWebDriver(new URL(completeUrl), capabilities);

				} else {
					capabilities = OptionsManager.getChromeOptions();
					driver = new RemoteWebDriver(new URL(completeUrl), capabilities);

					driver.manage().window().maximize();

				}
				log.info("Chrome remote driver initialized");

			} else {
				if (headless) {
					WebDriverManager.chromedriver().setup();

					driver = new ChromeDriver(OptionsManager.getHeadlessChromeOptions());
					log.info("Chrome local driver initialized");

				} else {
					WebDriverManager.chromedriver().setup();

					driver = new ChromeDriver(OptionsManager.getChromeOptions());
					driver.manage().window().maximize();
					log.info("Chrome local driver initialized");
				}
			}

		} else if (platformName.equalsIgnoreCase(PlatformName.FIREFOX.name())) {
			if (host != null) {
				String completeUrl = "http://" + host + ":4444/wd/hub";

				capabilities = OptionsManager.getFirefoxOptions();
				driver = new RemoteWebDriver(new URL(completeUrl), capabilities);
				log.info("Firefox remote driver initialized");
			} else {
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
				driver.manage().window().maximize();
				log.info("Firefox local driver initialized");
			}
		} else if (platformName.equalsIgnoreCase(PlatformName.IE.name())) {
			if (host != null) {
				String completeUrl = "http://" + host + ":4444/wd/hub";

				capabilities = OptionsManager.getIEOptions();
				driver = new RemoteWebDriver(new URL(completeUrl), capabilities);
				log.info("IE remote driver initialized");
			} else {
				WebDriverManager.iedriver().setup();
				driver = new InternetExplorerDriver();
				driver.manage().window().maximize();
				log.info("IE local driver initialized");
			}
		} else if (platformName.equalsIgnoreCase(PlatformName.ZAP_PROXY.name())) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver(OptionsManager.getChromeOptions()
					.setProxy(ZapScan.createZapProxyConfigurationForWebDriver()));
			driver.manage().window().maximize();
			log.info("Chrome local driver initialized With ZAP Proxy");
		}

		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		setExecutionPlatform(platformName);

		return driver;
	}
}
