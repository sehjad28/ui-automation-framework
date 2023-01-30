/*
 * @author Sampad Rout
 * (C) Copyright 2020 by Cinch Home Services, Inc.
 */

package com.testautomation.ui.reporting;

import com.testautomation.ui.drivers.WebDriverManager;
import com.testautomation.ui.utilities.LoadLogger;
import io.qameta.allure.Attachment;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    static Logger log = Logger.getLogger(LoadLogger.class);

    private static String getMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getMethodName();
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        LoadLogger.getInstance();
        log.info("TEST START: "+iTestResult.getMethod().getMethodName()+" - "+iTestResult.getMethod().getDescription());
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        LoadLogger.getInstance();
        log.info("TEST PASS: "+iTestResult.getMethod().getMethodName()+" - "+iTestResult.getMethod().getDescription());
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        LoadLogger.getInstance();

        log.info("TEST FAIL: "+iTestResult.getMethod().getMethodName()+" - "+iTestResult.getMethod().getDescription());
        Object testClass = iTestResult.getInstance();

        saveScreenshot(WebDriverManager.getWebDriverThread());
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        LoadLogger.getInstance();
        log.info("TEST SKIP: "+iTestResult.getMethod().getMethodName()+" - "+iTestResult.getMethod().getDescription());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        LoadLogger.getInstance();
        log.info("TEST FAIL WITH BUSINESS REQ: "+iTestResult.getMethod().getMethodName()+" - "+iTestResult.getMethod().getDescription());
    }

    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onFinish(ITestContext context) {
        LoadLogger.getInstance();
        log.info("TEST FINISH: "+context.getName());
    }

    @Attachment(value = "Actual Screenshot", type = "image/png")
    public byte[] saveScreenshot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}
