package com.sparta.aa.testframework;

import com.sparta.aa.testframework.lib.pages.HomePage;
import com.sparta.aa.testframework.lib.pages.PastPage;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.*;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.net.PasswordAuthentication;

public class MyStepdefs {
    private static final String DRIVER_LOCATION = "src/test/resources/chromedriver-win64/chromedriver.exe";

    private static final String BASE_URL = "https://news.ycombinator.com/";
    private static ChromeDriverService service;

    private WebDriver webDriver;
    private HomePage homePage;
    private PastPage pastPage;


    public static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--start-maximized");
        options.addArguments("--headless");
        options.addArguments("--remote-allow-origins=*");
//        options.setImplicitWaitTimeout(Duration.ofSeconds(10));
        return options;
    }

    @BeforeAll
    public static void beforeAll() throws IOException {
        service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File(DRIVER_LOCATION))
                .usingAnyFreePort()
                .build();
        service.start();
        System.out.println("🟢Start Server...");
    }

    @Before
    public void setup() {
        webDriver = new RemoteWebDriver(service.getUrl(), getChromeOptions());
        System.out.println("🧪Start Test...");
//        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

    }

    @After
    public void afterEach() {
        webDriver.quit();
        System.out.println("🔚Finished Test case.");
    }

    @AfterAll
    static void afterAll() {
        service.stop();
        System.out.println("🔴End of Server.");
    }


    @Given("I am on the Hacker News Homepage")
    public void iAmOnTheHackerNewsHomepage() {
        webDriver.get(BASE_URL);
        homePage = new HomePage(webDriver);


    }

    @When("I click on the Past link")
    public void iClickOnThePastLink() {
        pastPage = homePage.goToPastPage();
    }

    @Then("I should be taken to the Past Page")
    public void iShouldBeTakenToThePastPage() {
        MatcherAssert.assertThat(pastPage.getUrl(), Matchers.is("https://news.ycombinator.com/front"));
    }
}
