package com.sparta.aa.testframework.lib.pages;

import org.openqa.selenium.WebDriver;

import javax.xml.stream.events.Comment;

public class CommentsPage {
    private final WebDriver webDriver;

    public CommentsPage(WebDriver webDriver){
        this.webDriver = webDriver;
    }

    public String getCurrentUrl() {
        return webDriver.getCurrentUrl();
    }

    public String getTitle() {
        return webDriver.getTitle();
    }
}
