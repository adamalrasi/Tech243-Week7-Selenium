package com.sparta.aa.testframework.lib.pages;

import org.openqa.selenium.WebDriver;

public class SearchPage {

    private final WebDriver webDriver;

    public SearchPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public String getCurrentUrl() {
        return webDriver.getCurrentUrl();
    }
}
