/*
 * @author Sampad Rout
 * (C) Copyright 2020 by Cinch Home Services, Inc.
 */

package com.testautomation.ui.drivers;

import com.testautomation.ui.config.DeviceConfig;
import com.testautomation.ui.utilities.FileUtility;
import com.testautomation.ui.utilities.LoadLogger;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import com.testautomation.ui.config.IOSDeviceModel;
import org.openqa.selenium.remote.DesiredCapabilities;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class IOSDriverBuilder extends DeviceConfig {

    static Logger log = Logger.getLogger(LoadLogger.class);

    IOSDriver driver;

    public IOSDriver setupDriver(String model, String cloud) throws IOException {
        LoadLogger.getInstance();

        if (cloud.equalsIgnoreCase("saucelabs")) {
            IOSDeviceModel device = readIOSDeviceConfig().getIOSDeviceByName(model);
            log.info("Received the " + model + " device configuration for execution");
            setExecutionPlatform(model);

            DesiredCapabilities iosCapabilities = new DesiredCapabilities();
            iosCapabilities.setCapability(MobileCapabilityType.APPIUM_VERSION, device.getAppiumVersion());
            iosCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, device.getDeviceName());
            iosCapabilities.setCapability("deviceOrientation", device.getDeviceOrientation());
            iosCapabilities.setCapability(MobileCapabilityType.BROWSER_NAME, device.getBrowserName());
            iosCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, device.getPlatformVersion());
            iosCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, device.getPlatformName());
            iosCapabilities.setCapability(MobileCapabilityType.APP, device.getApp());

            // Here the sauce lab user name and access key are static
            driver = new IOSDriver(new URL("https://sampad:2d91985a-5b48-4c16-a5ea-1748e580d47b@ondemand.us-west-1.saucelabs.com:443/wd/hub"), iosCapabilities);
            driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
            log.info("Android driver has been created for the " + model + " device");
            return driver;
        } else if (cloud.equalsIgnoreCase("browserstack")) {
            IOSDeviceModel device = readIOSDeviceConfig().getIOSDeviceByName(model);
            log.info("Received the " + model + " device configuration for execution");
            setExecutionPlatform(model);

            DesiredCapabilities iosCapabilities = new DesiredCapabilities();
            iosCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, device.getDeviceName());
            iosCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, device.getPlatformVersion());
            iosCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, device.getPlatformName());
            iosCapabilities.setCapability(MobileCapabilityType.APP, device.getApp());

            iosCapabilities.setCapability("project", device.getProject());
            iosCapabilities.setCapability("build", device.getBuild());
            iosCapabilities.setCapability("test_name", device.getTest_name());

            iosCapabilities.setCapability("autoAcceptAlerts", "true");
            iosCapabilities.setCapability("browserName", "Safari");
            iosCapabilities.setCapability("nativeWebTap", true);

            // Here the sauce lab user name and access key are static
            driver = new IOSDriver(new URL("http://sampadrout_8zl59X:DR9UKDep4MqV4DdWZwaa@hub-cloud.browserstack.com/wd/hub"), iosCapabilities);
            driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
            log.info("Android driver has been created for the " + model + " device");
            return driver;
        } else {
            IOSDeviceModel device = readIOSDeviceConfig().getIOSDeviceByName(model);
            log.info("Received the " + model + " device configuration for execution");
            setExecutionPlatform(model);

            DesiredCapabilities iosCapabilities = new DesiredCapabilities();
            iosCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, device.getDeviceName());
            iosCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, device.getPlatformName());
            iosCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, device.getPlatformVersion());
            iosCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, device.getAutomationName());
            iosCapabilities.setCapability(MobileCapabilityType.UDID, device.getUdid());
            iosCapabilities.setCapability(MobileCapabilityType.NO_RESET, device.isReset());
            iosCapabilities.setCapability(MobileCapabilityType.APP, FileUtility.getFile(device.getApp()).getAbsolutePath());

            driver = new IOSDriver(new URL("http://localhost:4723/wd/hub"), iosCapabilities);
            driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
            log.info("IOS driver has been created for the " + model + " device");
            return driver;

        }
    }
}
