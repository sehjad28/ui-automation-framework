/*
 * @author Sampad Rout
 * (C) Copyright 2020 by Cinch Home Services, Inc.
 */

package com.testautomation.ui.drivers;

import com.testautomation.ui.config.DeviceConfig;
import com.testautomation.ui.utilities.FileUtility;
import com.testautomation.ui.utilities.LoadLogger;
import com.testautomation.ui.config.AndroidDeviceModel;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

public class AndroidDriverBuilder extends DeviceConfig {

    static Logger log = Logger.getLogger(LoadLogger.class);

    AndroidDriver<WebElement> driver;

    public AndroidDriver<WebElement> setupDriver(String model, String cloud) throws IOException {
        LoadLogger.getInstance();

        if (cloud.equalsIgnoreCase("saucelabs")) {
            AndroidDeviceModel device = readAndroidDeviceConfig().getAndroidDeviceByName(model);
            log.info("Received the " + model + " device configuration for execution");
            setExecutionPlatform(model);

            DesiredCapabilities androidCapabilities = new DesiredCapabilities();
            androidCapabilities.setCapability(MobileCapabilityType.APPIUM_VERSION, device.getAppiumVersion());
            androidCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, device.getDeviceName());
            androidCapabilities.setCapability("deviceOrientation", device.getDeviceOrientation());
            androidCapabilities.setCapability(MobileCapabilityType.BROWSER_NAME, device.getBrowserName());
            androidCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, device.getPlatformVersion());
            androidCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, device.getPlatformName());
            androidCapabilities.setCapability(MobileCapabilityType.APP, device.getApp());
            androidCapabilities.setCapability("autoGrantPermissions", "true");

            // Here the sauce lab user name and access key are static
            driver = new AndroidDriver<>(new URL("https://sampadqa1:20f044eafa1046439bde5a59848124c2@ondemand.us-west-1.saucelabs.com:443/wd/hub"), androidCapabilities);
            driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
            log.info("Android driver has been created for the " + model + " device");
            return driver;

        } else if (cloud.equalsIgnoreCase("browserstack")) {
            AndroidDeviceModel device = readAndroidDeviceConfig().getAndroidDeviceByName(model);
            log.info("Received the " + model + " device configuration for execution");
            setExecutionPlatform(model);

            DesiredCapabilities androidCapabilities = new DesiredCapabilities();
            androidCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, device.getDeviceName());
            androidCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, device.getPlatformVersion());
            androidCapabilities.setCapability("browserstack.appium_version", device.getAppiumVersion());
            androidCapabilities.setCapability("name", "SP mobile test");
            androidCapabilities.setCapability("browserstack.debug", true);
            androidCapabilities.setCapability(MobileCapabilityType.APP, device.getApp());

            androidCapabilities.setCapability("project", device.getProject());
            androidCapabilities.setCapability("build", device.getBuild());
            androidCapabilities.setCapability("test_name", device.getTest_name());

            driver = new AndroidDriver<>(new URL("http://sampadrout_8zl59X:DR9UKDep4MqV4DdWZwaa@hub-cloud.browserstack.com/wd/hub"), androidCapabilities);
            driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
            log.info("Android driver has been created for the " + model + " device");
            return driver;
        } else if (cloud.equalsIgnoreCase("testobject")) {
            AndroidDeviceModel device = readAndroidDeviceConfig().getAndroidDeviceByName(model);
            log.info("Received the " + model + " device configuration for execution");
            setExecutionPlatform(model);

            DesiredCapabilities androidCapabilities = new DesiredCapabilities();
            androidCapabilities.setCapability(MobileCapabilityType.APPIUM_VERSION, device.getAppiumVersion());
            androidCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, device.getDeviceName());
            androidCapabilities.setCapability("deviceOrientation", device.getDeviceOrientation());
            androidCapabilities.setCapability(MobileCapabilityType.BROWSER_NAME, device.getBrowserName());
            androidCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, device.getPlatformVersion());
            androidCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, device.getPlatformName());
            androidCapabilities.setCapability(MobileCapabilityType.APP, device.getTestobject_app_id());
            androidCapabilities.setCapability("testobject_api_key", "97C14FC7FEF6490B886A988FBBD8E8BF");

            driver = new AndroidDriver<>(new URL("https://us1.appium.testobject.com/wd/hub"), androidCapabilities);
            driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
            log.info("Android driver has been created for the " + model + " device");
            return driver;
        } else if (cloud.equalsIgnoreCase("perfecto")) {
            AndroidDeviceModel device = readAndroidDeviceConfig().getAndroidDeviceByName(model);
            log.info("Received the " + model + " device configuration for execution");
            setExecutionPlatform(model);

            DesiredCapabilities androidCapabilities = new DesiredCapabilities();
            androidCapabilities.setCapability("platformName", "Android");
            androidCapabilities.setCapability("platformVersion", "11");
            androidCapabilities.setCapability("location", "NA-US-BOS");
            androidCapabilities.setCapability("resolution", "1080x2280");
            androidCapabilities.setCapability("manufacturer", "Google");
            androidCapabilities.setCapability("model", device.getModel());
            androidCapabilities.setCapability("enableAppiumBehavior", true);
            androidCapabilities.setCapability("openDeviceTimeout", 2);
            androidCapabilities.setCapability("app", "PRIVATE:app-appstore-release_QA.apk"); // Set Perfecto Media repository path of App under test.
            androidCapabilities.setCapability("appPackage", "com.mendix"); // Set the unique identifier of your app
            androidCapabilities.setCapability("appActivity", "com.mendix.nativetemplate.MainActivity");
            androidCapabilities.setCapability("autoLaunch", true); // Whether to install and launch the app automatically.
            androidCapabilities.setCapability("autoInstrument", true); // To work with hybrid applications, install the iOS/Android application as instrumented.
            androidCapabilities.setCapability("takesScreenshot", false);
            androidCapabilities.setCapability("screenshotOnError", true); // Take screenshot only on errors
            androidCapabilities.setCapability("securityToken", "eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI2ZDM2NmJiNS01NDAyLTQ4MmMtYTVhOC1kODZhODk4MDYyZjIifQ.eyJpYXQiOjE2MjEyNjA0MjgsImp0aSI6IjEyNGZhZmQ0LTdiOTEtNDFmMS1iMGVjLWI0ZjRmMmMzYzE1OSIsImlzcyI6Imh0dHBzOi8vYXV0aDMucGVyZmVjdG9tb2JpbGUuY29tL2F1dGgvcmVhbG1zL3RyaWFsLXBlcmZlY3RvbW9iaWxlLWNvbSIsImF1ZCI6Imh0dHBzOi8vYXV0aDMucGVyZmVjdG9tb2JpbGUuY29tL2F1dGgvcmVhbG1zL3RyaWFsLXBlcmZlY3RvbW9iaWxlLWNvbSIsInN1YiI6IjU5ZmE5NzQzLTY3MzQtNGVkNS04ODUzLWEzZTYxOWY4MjZhYSIsInR5cCI6Ik9mZmxpbmUiLCJhenAiOiJvZmZsaW5lLXRva2VuLWdlbmVyYXRvciIsIm5vbmNlIjoiYzlmMDY0NDgtMjc5MC00ODY3LTg1M2MtOWUxNzUxMGY4YjhjIiwic2Vzc2lvbl9zdGF0ZSI6ImFjZGVkZjUxLTdkZmQtNDIwZi1hZWM5LWE0MGNjN2NkY2IwYyIsInNjb3BlIjoib3BlbmlkIG9mZmxpbmVfYWNjZXNzIHByb2ZpbGUgZW1haWwifQ.MmAoCKCN_2rtApDeMUZ2ZQPDrejUd9VULh3F-vJn630");

            driver = new AndroidDriver<>(new URL("https://trial.perfectomobile.com/nexperience/perfectomobile/wd/hub"), androidCapabilities);
            driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
            log.info("Android driver has been created for the " + model + " device");
            return driver;
        } else {
            WebDriverManager.chromedriver().setup();

            DesiredCapabilities androidCapabilities = new DesiredCapabilities();
            AndroidDeviceModel device = readAndroidDeviceConfig().getAndroidDeviceByName(model);
            log.info("Received the " + model + " device configuration for execution");
            setExecutionPlatform(model);

            androidCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, device.getPlatformName());
            androidCapabilities.setCapability(MobileCapabilityType.APP, FileUtility.getFile(device.getApp()).getAbsolutePath());
            androidCapabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, device.getAppActivity());
            androidCapabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, device.getAppPackage());
            androidCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, device.getAutomationName());
            androidCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, device.getDeviceName());
            androidCapabilities.setCapability(MobileCapabilityType.FULL_RESET, device.isFullReset());
            androidCapabilities.setCapability(MobileCapabilityType.NO_RESET, device.isNoReset());
            androidCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, device.getPlatformVersion());
            androidCapabilities.setCapability(AndroidMobileCapabilityType.CHROMEDRIVER_CHROME_MAPPING_FILE, "src/main/resources/mapping.json");
            androidCapabilities.setCapability(MobileCapabilityType.SUPPORTS_ALERTS, true);

            driver = new AndroidDriver<>(new URL("http://localhost:4723/wd/hub"), androidCapabilities);
            driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
            log.info("Android driver has been created for the " + model + " device");
            return driver;

        }
    }
}
