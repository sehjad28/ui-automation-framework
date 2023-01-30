# `UI Framework`
<br>This is a generic Page Object Model for web, android and iOS test automation with single codebase.<br>
<b>It requires test cases and corresponding page objects to be created. The same needs to be refeerred in testng.xml. Here testng.xml requires the platform name, platform type and model name to run the tests on web or mobile and also on chrome, safari browsers or on android or iOS devices.</b><br>
## Contents:
* [Automation Framework Architecture](#automation-framework-architecture)
* [Features](#features)
* [Libraries Used](#libraries-used)
* [Prerequisites Installations](#prerequisites-installations)
* [Appium Setup](#appium-setup)
* [How This Framework Works](#how-this-framework-works)
* [How To Run Tests](#how-to-run-tests)
* [How To See Allure Result Report](#how-to-see-allure-result-report)
* [Visual Tests](#visual-tests)
* [Accessibility Tests](#accessibility-tests)

## Automation Framework Architecture:
<img width="894" alt="UI Automation Framework" src="https://user-images.githubusercontent.com/64055910/176699499-04a03c2b-4f95-4633-9698-5ea266a069da.png">

## Features:
* Cross platform(mobile & web) support with single codebase
* Page Object Model
* TestNG integration
* Supports test data management using NoSQL db like MongoDB, DocumentDB, Excel
* Allure Reporting
* Parallel and Distributed Execution on Grid running on Cloud
* Ability to integrate with the Device Labs like Sauce Labs and Browserstack.com
* Supports Visual testing
* Supports Accessibility Testing
* Framework is flexible enough to accommodate other test types like api, security etc.
## Libraries Used:
1. Selenium WebDriver
2. Appium
3. Java
4. TestNG
5. Maven
6. WebDriverManager
7. MongoDB
8. AShot
9. Allure Report
10. Jenkins
11. Selenium Grid hosted on AWS
## Prerequisites Installations:
1. <b>`JAVA 1.8`</b> - Install Java and set the JAVA_HOME path on your machine.
2. <b>`Node & NPM`</b> - Download & install node from `https://nodejs.org/en/download/`.
3. <b>`Maven`</b> - Install Maven.
4. <b>`Android`</b> - Install Android Studio & set <i><b>ANDROID_HOME</b></i> path.<br>
    -  Downloading the Android SDK
    -  Download the Android SDK tools such as 
       1. Build tools
       2. Platform tools
       3. Android Emulator
       4. Intel HAXM installer etc.....
       5. Create an emulator device from AVD manager   
5. <b>`iOS`</b> - Install XCode on your machine & download required iPhone/iPad simulators.<br>
6. <b>`Allure Report`</b> - Install Allure Report library on your machine. Please follow below link to install it on MAC.<br>
    Similarly install allure-report installer on your respective machine.
    https://docs.qameta.io/allure/#_installing_a_commandline

<b>`Note: If you want to run only on WEB, you don't need anything except JAVA.`</b><br>
<b>`Mentioned installations Node, Android & iOS are for mobile app automation & Rest like Maven & Allure are for framework level`</b>
## Appium Setup:
- <b>`Install Appium`</b> 
``` 
 $ sudo npm install -g appium@1.9.1 --unsafe-perm=true --allow-root 
```
- <b>`Appium Doctor`</b> - which is used to see if the appium setup is correctly done or not. Run it and fix the issues as per that.<br>
``` 
 $ sudo npm install -g appium-doctor --unsafe-perm=true --allow-root
 $ appium-doctor
```
## How This Framework Works:
This framework is built in Page Object Model style using TestNG framework.<br>
Please use "testng.xml" file which has tests for each and every platform in cross browser/device testing fashion.
###### Here are the minimal things you have to do:
 - Create your tests
 - Create your Page Object class w.r.t test that you have written
 - If mobile app, Set the android, ios device details in corresponding files in resources directory
 - If web app, Set web app URL in database
# how to run tests
 ## List of available command-line parameters
- > **-Dcloud** = This is to specify where we want to run the tests, that's on **local** or on device labs like **browserstack**, **saucelabs**
- > **-Denvironment** = This accepts following environments **qa, preprod, preprod2, prod**
- > **-DtestType** = Here we can specify what type of tests we want to run that's **functional, visual, accessibility**
- > **-DHUB_HOST** = This is to specify the private Selenium Grid hub that's **http://qa-selenium-hub-nlb-bae199390c5e1ae7.elb.us-east-1.amazonaws.com/**
- > **-Dheadless** = This is to specify whether we want to run the web tests headless. This accepts **true** or **false**.
## How To Run Tests for Web:
### Functional Test
```
$ mvn clean test -Denvironment=qa -DtestType=functional -DHUB_HOST=qa-selenium-hub-nlb-bae199390c5e1ae7.elb.us-east-1.amazonaws.com -Dheadless=true
```
### Accessibility Test
```
$ mvn clean test -Denvironment=qa -DtestType=accessibility -DHUB_HOST=qa-selenium-hub-nlb-bae199390c5e1ae7.elb.us-east-1.amazonaws.com -Dheadless=true
```
### Visual Test
```
$ mvn clean test -Denvironment=qa -DtestType=visual -DHUB_HOST=qa-selenium-hub-nlb-bae199390c5e1ae7.elb.us-east-1.amazonaws.com -Dheadless=true
```
## How To Run Tests for Mobile:
#### Running test locally
```
$ mvn clean test -DtestType=functional -Denvironment=qa -Dcloud=local
```
#### Running test on Browserstack
```
$ mvn clean test -DtestType=functional -Denvironment=qa -Dcloud=browserstack
```
> **-Denvironment=** It takes values like qa, preprod, preprod2 and prod.

> **-Dcloud=** It takes values like local, browserstack, saucelabs etc. For running the tests locally, please set it as local. For local tests, framework takes care of starting the appium server only for **MAC**. For **Windows** machine, please start the appium server manually and then start the tests. Here the appium server can have any IP address.

While running the mobile specifi tests, please provide additional testng parameter "model" in the testng.xml as below:
```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd">
<suite name="SP Mobile Test Suite">
    <test name="Sign in Test - android">
        <parameter name="platformType" value="mobile"/>
        <parameter name="platformName" value="android"/>
        <parameter name="model" value="pixel"/>
        <classes>
            <class name="com.cinch.sp.mobile.tests.signin.Signin_Test"/>
        </classes>
    </test>
    <test name="Sign in Test - iOS">
        <parameter name="platformType" value="mobile"/>
        <parameter name="platformName" value="ios"/>
        <parameter name="model" value="iphone11"/>
        <classes>
            <class name="com.cinch.sp.mobile.tests.signin.Signin_Test"/>
        </classes>
    </test>
</suite>
```
It also requires to provide device specific capabilities and the file as androidDevice.json and iosDevice.json under project folder test/resources as below:

> androidDevice.json
```
[
    {
        "name": "Pixel",
        "deviceName": "emulator-5556",
        "platformName": "Android",
        "platformVersion": "11",
        "automationName": "UIAutomator2",
        "appPackage": "com.cinch.cinchpro",
        "appActivity": "com.mendix.nativetemplate.MainActivity",
        "noReset": true,
        "fullReset": false,
        "app": "/Users/sampadrout/Downloads/app-appstore-release_preprod.apk"
    }
]
```
> iosDevice.json
```
[
  {
    "deviceName": "iPhone X",
    "platformName": "iOS",
    "platformVersion": "12.4",
    "automationName": "XCUITest",
    "name": "iphone11",
    "app": "/Users/sampadrout/Downloads/CinchPro_Preprod.ipa",
    "noReset": true
  }
]
```
## How To See Allure Result Report:
Once test execution is complete, allure-results directory gets generated. I assume you have already installed allure on your machine. If not, install it. If yes, run below command to see the report.
```
$ allure serve <allure-results path>
```
Allure report can also be created using maven command as below:
```
$ mvn allure:report
```
> The report created under: target/site/index.html.
## Visual Tests:
Visual test components are now integrated with Core framework and available for all web tests.
The AShot library used here to take the screenshot of a particular page and saved it as the baseline image.
This again compares with the current image taken during the runtime and in case of any differences, capture the same saved as a separate file with the differences highlighted in red color.

There is one additional parameter **visual** introduced and can have values like **capture** or **compare**.
More on Ashot can be found [here](https://github.com/pazone/ashot).
### How to create a visual test
- Create a test class as below:
```
package com.cinch.agentui.visual.tests;

import com.cinch.agentui.companycreation.HomePage;
import BaseTest;
import GetDataFromMongo;
import TestNGMongoDataProvider;
import CustomSoftAssert;
import Wait;
import ImageComparator;

import io.qameta.allure.Description;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Map;

public class Signin_VisualTest extends BaseTest {
    SoftAssert softAssert = new SoftAssert();
    Wait wait = new Wait();

    @GetDataFromMongo(dbName = "accel_agent", collectionName = "environment_ui", appName = "accel_agent", envName = "environment", tcName = "C34424", dataType = "testcaseData")
    @Test(description = "C36824:Creation of Company from Agent Web UI-Click the Coverage", priority = 1, dataProviderClass = TestNGMongoDataProvider.class, dataProvider = "dataProviderMongo")
    @Description("Creation of Company from Agent Web UI-Click the Coverage ")
    public void signinVisualTest(Map<String, String> user_details) throws Exception {
        page.getPageInstance(HomePage.class).gotoSite(user_details.get("url"));
        wait.staticWait(5000);
        CustomSoftAssert.assertTrue(new ImageComparator(driver).compare("homePage"), "homePage", softAssert);
        softAssert.assertAll();
    }
}
```
Here, give the name of the image as per the page name. ImageComparator class is having compare method to either capture or compare images.
This operation governed by the testng.xml parameter **visual** = **capture** or **compare**.
We have created a custom soft assertion class to send appropriate test status to allure report and in case of failure, framework will attach the expected, actual and diff images to the report.

- Now add this test to testng.xml as below:
```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd">
<suite name="Agent Web Visual Test Suite">

    <test name="Agent Web - Sign in Visual Test">
        <parameter name="platformType" value="web"/>
        <parameter name="platformName" value="chrome"/>
        <parameter name="visual" value="capture"/>
        <classes>
            <class name="com.cinch.agentui.visual.tests.Signin_VisualTest"/>
        </classes>
    </test>
</suite>
```
This will run the test in capture mode and create a baseline image under Test > Resources > baselineimages > [platformName like chrome]
- Once the baseline images are captured, check-in the images along with test classes to GIT. In that way, we are retaining the baseline image. While running the visual tests in jenkins, we need to compare the current images with the baseline images.
- To run the test in compare mode, update the testng.xml with visual=compare:
```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd">
<suite name="Agent Web Visual Test Suite">

    <test name="Agent Web - Sign in Visual Test">
        <parameter name="platformType" value="web"/>
        <parameter name="platformName" value="chrome"/>
        <parameter name="visual" value="compare"/>
        <classes>
            <class name="com.cinch.agentui.visual.tests.Signin_VisualTest"/>
        </classes>
    </test>
</suite>
```
To run visual tests, following JVM parameters are required:
```
$ mvn clean test -Denvironment=qa -DtestType=visual
```
## Accessibility Tests:
Accessibility test components are now integrated with Core framework and available for all web tests.
The axe-core library used here to analyse the accessibility issues present in the website.
This library provided as open source by [Deque Systems Inc.](https://www.deque.com/).
More information on this library can be found [here](https://github.com/dequelabs/axe-core-maven-html).
The axe-core supports the following accessibility standards:
1. WCAG 2.0 Level A & AA Rules
2. WCAG 2.1 Level A & AA Rules
3. WCAG 2.0 and 2.1 level AAA rules
4. Best Practices Rules

The rule descriptions can be checked [here](https://github.com/dequelabs/axe-core/blob/develop/doc/rule-descriptions.md)
## How to create an Accessibility Test
- Create a test class as below:
```
package com.cinch.agentui.accessibility.tests;

import java.util.Map;

import CheckAxe;
import CustomSoftAssert;
import com.cinch.agentui.companycreation.DashboardPage;
import com.cinch.agentui.companycreation.HomePage;
import com.cinch.agentui.companycreation.SignInPage;
import BaseTest;
import GetDataFromMongo;
import TestNGMongoDataProvider;
import TestListener;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.qameta.allure.Description;

@Listeners({TestListener.class})
public class Dashboard_axeTest extends BaseTest {

    SoftAssert softAssert = new SoftAssert();

    @GetDataFromMongo(dbName = "accel_agent", collectionName = "environment_ui", appName = "accel_agent", envName = "environment", tcName = "C34590", dataType = "testcaseData")
    @Test(description = "Dashboard Accessibility Test", dataProviderClass = TestNGMongoDataProvider.class, dataProvider = "dataProviderMongo")
    @Description("This test is to accessibility check on Dashboard page")
    public void dashboardAccessibilityTest(Map<String, String> user_details) throws Exception {
        page.getPageInstance(HomePage.class).gotoSite(user_details.get("url"));
        page.getPageInstance(HomePage.class).chooseSignInOption();
        page.getPageInstance(SignInPage.class).signIn(user_details.get("username"),
                user_details.get("password"));
        softAssert.assertTrue(page.getPageInstance(DashboardPage.class).verifyDashboardPageDisplayed(),
                "Signin failed. Dashboard page not displayed.");
        CustomSoftAssert.axeSoftAssert(CheckAxe.checkAxe(driver), softAssert);

        softAssert.assertAll();
    }
}
```
Here in checkAxe() method, one needs to pass the driver instance and also the softAssert instance.
When axeSoftAssert() is called, its immediately check the page below which its called.
In the above code snippet, we have called this method being in Dashboard page.
So checkAxe() method analyses the accessibility issues for Dashboard page only.
- Now create a new test suite called axe-test-suite.xml and add this test as below:
```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd">
<suite name="Agent Web Accessibility Test Suite">

    <test name="Agent Web - Dashboard Accessibility Test">
        <parameter name="platformType" value="web"/>
        <parameter name="platformName" value="chrome"/>
        <parameter name="axe" value="critical"/>
        <classes>
            <class name="com.cinch.agentui.accessibility.tests.Dashboard_axeTest"/>
        </classes>
    </test>
</suite>
```
Here one can notice that the  value="critical". 
By specifying this, one will analyse the web page to find any "critical" accessibility issues.
We support all types of violations provided through the testng.xml file. instead of just one type of violation like "critical" or "moderate, the user can also list all types of violations by using the custom axe type "all". Ex:
```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd">
<suite name="Agent Web Accessibility Test Suite">

    <test name="Agent Web - Dashboard Accessibility Test">
        <parameter name="platformType" value="web"/>
        <parameter name="platformName" value="chrome"/>
        <parameter name="axe" value="all"/>
        <classes>
            <class name="com.cinch.agentui.accessibility.tests.Dashboard_axeTest"/>
        </classes>
    </test>
</suite>
```

To run accessibility tests, following JVM parameters are required
```
$ mvn clean test -Denvironment=qa -DtestType=accessibility
```
