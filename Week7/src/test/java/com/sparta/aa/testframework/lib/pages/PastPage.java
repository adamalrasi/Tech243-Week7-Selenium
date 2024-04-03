package com.sparta.aa.testframework.lib.pages;

import org.openqa.selenium.WebDriver;

public class PastPage {
    private final WebDriver webDriver;

    public PastPage(WebDriver webDriver) {

        if (!webDriver.getTitle().contains("front | Hacker News")) {

            throw new IllegalStateException("This is not the past Page," +

                    " current page is: " + webDriver.getCurrentUrl());
        }
        this.webDriver = webDriver;

    }    public String getUrl() {

        return this.webDriver.getCurrentUrl();

    }

    public String getTitle() {
        return webDriver.getTitle();
    }
}
