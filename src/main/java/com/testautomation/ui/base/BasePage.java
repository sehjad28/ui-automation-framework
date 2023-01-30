/*
 * @author Sampad Rout
 * (C) Copyright 2020 by Cinch Home Services, Inc.
 */

package com.testautomation.ui.base;

import com.testautomation.ui.utilities.LoadLogger;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import org.apache.log4j.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

@SuppressWarnings({"rawtypes", "unchecked"})
public class BasePage extends ElementEvent {
    static Logger log = Logger.getLogger(LoadLogger.class);

    protected WebDriver webdriver;

    // SP Admin Web WalkMe Setting status
    public boolean status_WalkMeSettings;
    // getting the status of WalkMe setting
    public boolean getStatus_WalkMeSettings() {
        return status_WalkMeSettings;
    }
    // setting the status of WalkMe setting
    public void setStatus_WalkMeSettings(boolean status_WalkMeSettings) {
        this.status_WalkMeSettings = status_WalkMeSettings;
    }

    public BasePage(WebDriver webdriver) {
        this.webdriver = webdriver;
    }

    public <T extends BasePage> T getPageInstance(Class<T> classz) {
        LoadLogger.getInstance();

        try {
            // Initialize the WebElements
            T pageClass = PageFactory.initElements(webdriver, classz);

            // AppiumFieldDecorator decorates 1) all of the WebElement fields and
            // 2) List of WebElement that have @AndroidFindBy, @AndroidFindBys, or @iOSFindBy/@iOSFindBys annotation.
            // The fields like WebElement, RemoteWebElement, MobileElement, AndroidElement and IOSElement are allowed
            // to use with this decorator
            PageFactory.initElements(new AppiumFieldDecorator(webdriver, Duration.ofSeconds(30)), pageClass);

            log.info("Initializing the " + this.getClass().getSimpleName() + " elements");
            return pageClass;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}