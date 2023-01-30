/*
 * @author Sampad Rout
 * (C) Copyright 2020 by Cinch Home Services, Inc.
 */

package com.testautomation.ui.base;

import com.testautomation.ui.drivers.WebDriverManager;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import static io.appium.java_client.touch.WaitOptions.waitOptions;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

import static java.time.Duration.ofSeconds;

/*
 * Functions defined below
 * 1. click
 * 2. type
 * 3. doubleClick
 * 4. rightClick
 * 5. gotoURL
 * 6. getLabel
 * 7. verifyTextOnPage
 * 8. getElementByLabel
 */

public class ElementEvent {

	public static void click(WebElement webelement) {
		webelement.click();
	}

	public void click(String locator, String val_to_be_replaced, WebDriver driver) {
		try {
			if (val_to_be_replaced != null) {
				locator = locator.replace("{value}", val_to_be_replaced);
			}
			WebElement element = driver.findElement(By.xpath(locator));
			element.click();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void type(WebElement webelement, String webelementvalue) throws Exception {
		webelement.sendKeys(webelementvalue);
	}

	public static void clear(WebElement webelement) throws Exception {
		webelement.clear();
	}

	public static void doubleClick(WebElement webelement) throws Exception {
		Actions action = new Actions(WebDriverManager.getWebDriverThread());
		action.doubleClick(webelement).perform();
	}

	public static void rightClick(WebElement webelement) throws Exception {
		Actions action = new Actions(WebDriverManager.getWebDriverThread());
		action.contextClick(webelement).perform();
	}

	public static void gotoURL(String url) throws Exception {
		WebDriverManager.getWebDriverThread().navigate().to(url);
	}

	public static String getLabel(WebElement webelement) {
		String label = null;
		try {
			label = webelement.getText().toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return label;
	}

	public static boolean verifyTextOnPage(String text) {
		boolean isTextFound = false;

		try {
			String pagetitle = WebDriverManager.getWebDriverThread().getTitle();
			isTextFound = WebDriverManager.getWebDriverThread().getPageSource().contains(text);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (isTextFound)
			return true;
		else
			return false;
	}

	public static WebElement getElementByLabel(String label) {
		WebElement webelement = null;
		String pagetitle = WebDriverManager.getWebDriverThread().getTitle();
		try {
			webelement = WebDriverManager.getWebDriverThread().findElement(By.xpath("//label[contains(text(),'" + label + "')]"));
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return webelement;
	}

	public void javaExecutorClick(WebElement element, WebDriver driver) {
		try {
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", element);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static boolean checkElement(WebElement element) throws Exception {
		try {
			return element.isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public static String getText(WebElement element) throws Exception {
		return element.getText();
	}

	public  String[] getTexts(List<WebElement> element) throws InterruptedException{
		String[] texts = new String[element.size()];
		for (int i=0; i<element.size();i++){
			//System.out.println("Text :" + acc_ListOfallAccoridans.get(i).getText());
			texts[i]=element.get(i).getText();
		}
		return texts;
	}

	public void scrollIntoView(WebElement element, WebDriver driver) {
		try {
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].scrollIntoView();", element);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void select(WebElement element, String visibletext) throws Exception {
		Select dropdown = new Select(element);
		dropdown.selectByVisibleText(visibletext);
	}

	public boolean compareListTexts(String[] expectedListText, List<WebElement> element) throws InterruptedException {
		String[] actualText= getTexts(element);
		boolean Status = false;
		for (int i=0; i<actualText.length;i++){
			if(actualText[i].equals(expectedListText[i])){
				Status=true;
			}
			else {
				Status=false;
			}
		}
		return Status;
	}

	public static void mouseHover(WebDriver driver, WebElement element) throws Exception {
		Actions action = new Actions(driver);
		action.moveToElement(element).build().perform();
	}

	public static void verticalScroll(WebDriver driver) throws Exception {
		Dimension size = driver.manage().window().getSize();
		int start_Y = (int) (size.getHeight() * 0.75);
		int end_Y = (int) (size.getHeight() * 0.10);
		int start_end_X = (int) (size.getWidth() * 0.50);

		TouchAction swipe = new TouchAction((PerformsTouchActions) driver).press(PointOption.point(start_end_X, start_Y))
				.waitAction(waitOptions(ofSeconds(2)))
				.moveTo(PointOption.point(start_end_X, end_Y)).release().perform();
	}

	public static void verticalScrolldown(WebDriver driver) {
		Dimension size = driver.manage().window().getSize();
       /* int start_Y = (int) (size.getHeight() * 0.75);
        int end_Y = (int) (size.getHeight() * 0.10);*/

		int start_Y = 1735;//1733;
		int end_Y = 535; //702;
		int start_end_X = (int) (size.getWidth() * 0.50);

		TouchAction swipe = new TouchAction((PerformsTouchActions) driver).press(PointOption.point(start_end_X, start_Y))
				.waitAction(waitOptions(ofSeconds(2)))
				.moveTo(PointOption.point(start_end_X, end_Y)).release().perform();
	}
	public static void verticalScrollUp(WebDriver driver) {
		Dimension size = driver.manage().window().getSize();
		int start_Y = (int) (size.getHeight() * 0.40);
		int end_Y = (int) (size.getHeight() * 0.75);

      /*  int start_Y = 702;//1826;
        int end_Y = 1826; //702;
      */
		int start_end_X = (int) (size.getWidth() * 0.50);

		TouchAction swipe = new TouchAction((PerformsTouchActions) driver).press(PointOption.point(start_end_X, start_Y))
				.waitAction(waitOptions(ofSeconds(2)))
				.moveTo(PointOption.point(start_end_X, end_Y)).release().perform();
	}

	public static void verticalScrollUpJobDate(WebDriver driver) {
		Dimension size = driver.manage().window().getSize();
		int start_Y = (int) (size.getHeight() * 0.40);
		int end_Y = (int) (size.getHeight() * 0.85);

      /*  int start_Y = 702;//1826;
        int end_Y = 1826; //702;
      */
		int start_end_X = (int) (size.getWidth() * 0.50);

		TouchAction swipe = new TouchAction((PerformsTouchActions) driver).press(PointOption.point(start_end_X, start_Y))
				.waitAction(waitOptions(ofSeconds(2)))
				.moveTo(PointOption.point(start_end_X, end_Y)).release().perform();
	}

	protected String getWebContext(WebDriver driver) {
		ArrayList<String> contexts = new ArrayList(((AppiumDriver<?>)driver).getContextHandles());
		for (String context : contexts) {
			if (!context.equals("NATIVE_APP")) {
				return context;
			}
		}
		return null;
	}

	public void selectByValue(WebElement element, String valuetext) {
		Select dropdown = new Select(element);
		dropdown.selectByValue(valuetext);
	}
}
