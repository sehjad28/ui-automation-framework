/*
 * @author Sampad Rout
 * (C) Copyright 2020 by Cinch Home Services, Inc.
 */

package com.testautomation.ui.config;

import java.util.Arrays;

public class IOSDeviceModel {
    private String deviceName;
    private String platformName;
    private String platformVersion;
    private String automationName;
    private String udid;
    private String app;
    private boolean reset;
    private String name;

    private boolean fullReset;
    private boolean noReset;
    private String autoAcceptAlerts;

    private String appiumVersion;
    private String browserName;
    private String deviceOrientation;

    // browserstack specific caps
    private String project;
    private String build;
    private String test_name;

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

    private IOSDeviceModel[] iosDeviceModels;

    public boolean isFullReset() {
        return fullReset;
    }

    public void setFullReset(boolean fullReset) {
        this.fullReset = fullReset;
    }

    public boolean isNoReset() {
        return noReset;
    }

    public void setNoReset(boolean noReset) {
        this.noReset = noReset;
    }

    public String getAutoAcceptAlerts() {
        return autoAcceptAlerts;
    }

    public void setAutoAcceptAlerts(String autoAcceptAlerts) {
        this.autoAcceptAlerts = autoAcceptAlerts;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IOSDeviceModel() {}

    public IOSDeviceModel(IOSDeviceModel[] iosDeviceModels) {
        this.iosDeviceModels = iosDeviceModels;
    }

    public IOSDeviceModel getIOSDeviceByName(String deviceName) {
        return Arrays.stream(iosDeviceModels).filter(iosDeviceModel -> iosDeviceModel.getName().equalsIgnoreCase(deviceName)).findFirst().get();
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public void setPlatformVersion(String platformVersion) {
        this.platformVersion = platformVersion;
    }

    public void setAutomationName(String automationName) {
        this.automationName = automationName;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public void setReset(boolean reset) {
        this.reset = reset;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getPlatformName() {
        return platformName;
    }

    public String getPlatformVersion() {
        return platformVersion;
    }

    public String getAutomationName() {
        return automationName;
    }

    public String getUdid() {
        return udid;
    }

    public String getApp() {
        return app;
    }

    public boolean isReset() {
        return reset;
    }

}
