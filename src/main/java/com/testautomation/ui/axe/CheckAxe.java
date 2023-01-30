/*
 * @author Sampad Rout
 * (C) Copyright 2021 by Cinch Home Services, Inc.
 */

package com.testautomation.ui.axe;

import com.deque.html.axecore.results.Results;
import com.deque.html.axecore.results.Rule;
import com.deque.html.axecore.selenium.AxeBuilder;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class CheckAxe {
    public static List<Rule> checkAxe(WebDriver driver){
        Results rs = new AxeBuilder().analyze(driver, true);
        List<Rule> ls = rs.getViolations();
        return ls;
    }
}