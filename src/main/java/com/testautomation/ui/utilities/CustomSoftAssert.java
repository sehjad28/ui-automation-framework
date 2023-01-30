/*
 * @author Sampad Rout
 * (C) Copyright 2021 by Cinch Home Services, Inc.
 */

package com.testautomation.ui.utilities;

import com.deque.html.axecore.results.Rule;

import io.qameta.allure.Allure;

import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.util.List;

import static com.testautomation.ui.base.BaseTest.axeMode;

public class CustomSoftAssert {

    public static void visualSoftAssert(boolean result, String imgName, SoftAssert softassert) throws IOException {
        if (!result) {
            AttachToAllureReport.attachScreenshot(imgName);
            softassert.assertTrue(false, imgName + " baseline image isn't matching with actual image.");
        } else {
            softassert.assertTrue(true);
        }

    }

    public static void axeSoftAssert(List<Rule> ls, SoftAssert softAssert) {
        if (ls.size() > 0) {
            int count = 1;
            for (Rule l : ls) {
                if (l.getImpact().equalsIgnoreCase(axeMode)) {
                    Allure.link(
                            "IMPACT " + count + " = " + " " + l.getImpact() + " : " + l.getDescription(),
                            l.getHelpUrl() + "<br>");
                    count++;
                }
            }
            softAssert.assertTrue(false, "Test Failed Cause Of Accessibility Checks Failure. " +
                    "Please Click Links above To Get Into Details Of Failures");
        } else {
            softAssert.assertTrue(true);
        }
    }
}