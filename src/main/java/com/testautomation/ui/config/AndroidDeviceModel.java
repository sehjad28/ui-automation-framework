/*
 * @author Sampad Rout
 * (C) Copyright 2020 by Cinch Home Services, Inc.
 */

package com.testautomation.ui.config;

import java.util.Arrays;

public class AndroidDeviceModel {
    private String platformName;
    private String app;
    private String appActivity;
    private String appPackage;
    private String automationName;
    private String deviceName;
    private boolean fullReset;
    private boolean noReset;
    private String platformVersion;
    private String name;
    private String autoAcceptAlerts;

    private String appiumVersion;
    private String browserName;
    private String deviceOrientation;

    // browserstack specific caps
    private String project;
    private String build;
    private String test_name;

    // perfecto specific caps
    private String model;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public String getTest_name() {
        return test_name;
    }

    public void setTest_name(String test_name) {
        this.test_name = test_name;
    }

    // testobjdect specific caps
    private String testobject_app_id;
    private String testobject_api_key;

    private AndroidDeviceModel[] androidDeviceModels;

    public AndroidDeviceModel() {
    }

    public AndroidDeviceModel(AndroidDeviceModel[] androidDeviceModels) {
        this.androidDeviceModels = androidDeviceModels;
    }

    public AndroidDeviceModel getAndroidDeviceByName(String deviceName) {
        return Arrays.stream(androidDeviceModels).filter(androidDeviceModel -> androidDeviceModel
                .getName().equalsIgnoreCase(deviceName)).findFirst().get();
    }

    public String getAppiumVersion() {
        return appiumVersion;
    }

    public void setAppiumVersion(String appiumVersion) {
        this.appiumVersion = appiumVersion;
    }

    public String getBrowserName() {
        return browserName;
    }

    public void setBrowserName(String browserName) {
        this.browserName = browserName;
    }

    public String getDeviceOrientation() {
        return deviceOrientation;
    }

    public void setDeviceOrientation(String deviceOrientation) {
        this.deviceOrientation = deviceOrientation;
    }

    public String getTestobject_app_id() {
        return testobject_app_id;
    }

    public void setTestobject_app_id(String testobject_app_id) {
        this.testobject_app_id = testobject_app_id;
    }

    public String getTestobject_api_key() {
        return testobject_api_key;
    }

    public void setTestobject_api_key(String testobject_api_key) {
        this.testobject_api_key = testobject_api_key;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public void setAppActivity(String appActivity) {
        this.appActivity = appActivity;
    }

    public void setAppPackage(String appPackage) {
        this.appPackage = appPackage;
    }

    public void setAutomationName(String automationName) {
        this.automationName = automationName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void setFullReset(boolean fullReset) {
        this.fullReset = fullReset;
    }

    public void setNoReset(boolean noReset) {
        this.noReset = noReset;
    }

    public void setPlatformVersion(String platformVersion) {
        this.platformVersion = platformVersion;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAutoAcceptAlerts(String autoAcceptAlerts) {
        this.autoAcceptAlerts = autoAcceptAlerts;
    }

    public String getPlatformName() {
        return platformName;
    }

    public String getApp() {
        return app;
    }

    public String getAppActivity() {
        return appActivity;
    }

    public String getAppPackage() {
        return appPackage;
    }

    public String getAutomationName() {
        return automationName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public boolean isFullReset() {
        return fullReset;
    }

    public boolean isNoReset() {
        return noReset;
    }

    public String getPlatformVersion() {
        return platformVersion;
    }

    public String getName() {
        return name;
    }

    public String getAutoAcceptAlerts() {
        return autoAcceptAlerts;
    }

}
