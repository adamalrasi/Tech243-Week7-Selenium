package com.sparta.aa.testframework.lib.pages;

import org.openqa.selenium.WebDriver;

public class AskPage {
    private final WebDriver webDriver;

    public AskPage(WebDriver webDriver){
        this.webDriver = webDriver;
    }


    public String getCurrentUrl() {
        return this.webDriver.getCurrentUrl();
    }

    public String getTitle() {
        return this.webDriver.getTitle();
    }
}
