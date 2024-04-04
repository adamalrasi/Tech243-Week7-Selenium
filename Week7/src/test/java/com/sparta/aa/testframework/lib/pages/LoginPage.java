package com.sparta.aa.testframework.lib.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
    private final WebDriver webDriver;

    public LoginPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public String getUrl() {
        return this.webDriver.getCurrentUrl();
    }

    public String getTitle() {
        return this.webDriver.getTitle();
    }

    public WebElement findElement(String path) {
        return webDriver.findElement(By.xpath(path));
    }
}
