/*
 * @author Sampad Rout
 * (C) Copyright 2020 by Cinch Home Services, Inc.
 */

package com.testautomation.ui.utilities;

import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Wait {

    protected final int explicitWaitDefault = 90;

    /**
     * This method is for static wait
     *
     * @param millis
     */
    public void staticWait(final long millis) {
        try {
            TimeUnit.MILLISECONDS.sleep(millis);
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * To wait for button to be clickable
     *
     * @param driver
     * @param element
     */
    public WebElement waitForElementToBeClickable(final WebElement element, final WebDriver driver) {
        return new WebDriverWait(driver, this.explicitWaitDefault)
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * To wait for element to be visible
     *
     * @param driver
     * @param element
     */
    public WebElement waitForElementToBeVisible(final WebElement element, final WebDriver driver) {
        long s = System.currentTimeMillis();
        return new WebDriverWait(driver, this.explicitWaitDefault).until(ExpectedConditions.visibilityOf(element));
    }

    public Boolean waitForElementContainsAttribute(final WebElement element, final String attr, final String value, final WebDriver driver) {
        long s = System.currentTimeMillis();
        return new WebDriverWait(driver, this.explicitWaitDefault).until(ExpectedConditions.attributeContains(element, attr, value));
    }

    public Boolean waitForElementAttributeToBe(final WebElement element, final String attr, final String value, final WebDriver driver) {
        long s = System.currentTimeMillis();
        return new WebDriverWait(driver, this.explicitWaitDefault).until(ExpectedConditions.attributeToBe(element, attr, value));
    }

    public Boolean waitForElementToBeSelected(final WebElement element, final WebDriver driver) {
        long s = System.currentTimeMillis();
        return new WebDriverWait(driver, this.explicitWaitDefault).until(ExpectedConditions.elementToBeSelected(element));
    }

    public Boolean waitForTextToBePresentInElement(final WebElement element, final String text, final WebDriver driver) {
        long s = System.currentTimeMillis();
        return new WebDriverWait(driver, this.explicitWaitDefault).until(ExpectedConditions.textToBePresentInElement(element, text));
    }

    public Boolean waitForTextToBePresentInElementValue(final WebElement element, final String text, final WebDriver driver) {
        long s = System.currentTimeMillis();
        return new WebDriverWait(driver, this.explicitWaitDefault).until(ExpectedConditions.textToBePresentInElementValue(element, text));
    }

    /**
     * To wait for element (By) to be invisible
     *
     * @param driver
     * @param element
     */
    public Boolean waitForElementToBeInvisible(final WebElement element, final WebDriver driver) {
        long s = System.currentTimeMillis();
        return new WebDriverWait(driver, this.explicitWaitDefault)
                .until(ExpectedConditions.invisibilityOf(element));
    }

    public Boolean waitForElementsToBeInvisible(final List<WebElement> elements, final WebDriver driver) {
        final long s = System.currentTimeMillis();
        return new WebDriverWait(driver, this.explicitWaitDefault)
                .until(ExpectedConditions.invisibilityOfAllElements(elements));
    }

    /**
     * To wait for element to be visible for given amount of time
     *
     * @param element
     * @param driver
     * @param time
     */
    public WebElement waitForElementToBeVisible(final IOSElement element, final WebDriver driver, int time) {
        long s = System.currentTimeMillis();
        return new WebDriverWait(driver, time).until(ExpectedConditions.visibilityOf(element));
    }
}
