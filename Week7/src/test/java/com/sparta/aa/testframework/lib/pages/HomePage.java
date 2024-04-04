package com.sparta.aa.testframework.lib.pages;


import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {

    private WebDriver webDriver;
    private final By pastLink = By.linkText("past");
    private final By searchField = new By.ByName("q");

    private final By commentsLink = By.linkText("comments");
    private final By askLink = By.linkText("ask");

    private final By loginLink = By.linkText("login");

    public HomePage(WebDriver webDriver) {
        if (!webDriver.getTitle().equals("Hacker News")) {
            throw new IllegalStateException("This is not the Hacker News home page, current page is: " + webDriver.getCurrentUrl());
        }
        this.webDriver = webDriver;
    }

    public PastPage goToPastPage() {
        webDriver.findElement(pastLink).click();
        return new PastPage(webDriver);
    }

    public SearchPage searchingPrompt(String prompt) {
        webDriver.findElement(searchField).sendKeys(prompt, Keys.ENTER);
        return new SearchPage(webDriver);
    }

    public CommentsPage goToCommentsPage(){
        webDriver.findElement(commentsLink).click();
        return new CommentsPage(webDriver);
    }

    public AskPage goToAskPage(){
        webDriver.findElement(askLink).click();
        return new AskPage(webDriver);
    }

    public LoginPage goToLoginPage(){
        Wait<WebDriver> wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        webDriver.findElement(loginLink).click();
        wait.until(D -> D.findElement(By.xpath("//b")).getText().contains("Login"));
        return new LoginPage(webDriver);
    }

    public String getCurrentUrl() {
        return webDriver.getCurrentUrl();
    }

    public String getTitle() {
        return webDriver.getTitle();
    }
}
