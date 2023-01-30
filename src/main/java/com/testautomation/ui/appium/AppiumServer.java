/*
 * @author Sampad Rout
 * (C) Copyright 2020 by Cinch Home Services, Inc.
 */

package com.testautomation.ui.appium;

import com.testautomation.ui.utilities.LoadLogger;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class AppiumServer {
    static Logger log = Logger.getLogger(LoadLogger.class);

    public static AppiumDriverLocalService appium;

    public static void start() throws IOException {
        LoadLogger.getInstance();

        try {
            AppiumServiceBuilder builder = new AppiumServiceBuilder()
                    .withAppiumJS(new File("/usr/local/lib/node_modules/appium/build/lib/main.js"))
                    .withIPAddress("127.0.0.1")
                    .usingPort(4723)
                    //.withArgument(() -> "--allow-insecure","chromedriver_autodownload")
                    .withArgument(GeneralServerFlag.SESSION_OVERRIDE);

            appium = AppiumDriverLocalService.buildService(builder);
            appium.start();
            log.info("Appium server has been started");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stop() throws IOException {
        LoadLogger.getInstance();
        try {
            appium.stop();
            log.info("Appium server has been stopped");
        } catch (Exception e) {
            log.error("Appium server never started");
        }
    }

    public static void main(String[] args) throws IOException {
        start();
        stop();
    }
}
