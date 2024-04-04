package com.sparta.aa.testframework;

import com.sparta.aa.testframework.lib.pages.*;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.*;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.platform.engine.support.discovery.SelectorResolver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.net.PasswordAuthentication;
import java.time.LocalDate;

import static org.hamcrest.Matchers.containsString;

public class MyStepdefs {
    private static final String DRIVER_LOCATION = "src/test/resources/chromedriver-win64/chromedriver.exe";

    private static final String BASE_URL = "https://news.ycombinator.com/";
    private static ChromeDriverService service;

    private WebDriver webDriver;
    private HomePage homePage;
    private PastPage pastPage;
    private SearchPage searchPage;
    private LoginPage loginPage;
    private AskPage askPage;
    private CommentsPage commentsPage;

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
        System.out.println("🟢Start Server...");
        service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File(DRIVER_LOCATION))
                .usingAnyFreePort()
                .build();
        service.start();
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

    @And("the page title will include yesterday's date")
    public void thePageTitleWillIncludeYesterdaySDate() {
//        WebElement latestArticle = webDriver.findElement(By.className("age"));
//        MatcherAssert.assertThat(pastPage.getTitle(), containsString(latestArticle.getAttribute("title").split("T")[0]));
        LocalDate yesterday = LocalDate.now().minusDays(1);
        MatcherAssert.assertThat(pastPage.getTitle(), Matchers.containsString(yesterday.toString()));
    }

    @When("I enter {string} into the Search text box")
    public void iEnterIntoTheSearchTextBox(String arg0) {
        searchPage = homePage.searchingPrompt("java");
    }

    @Then("I will go to the search page with the url parameter {string}")
    public void iWillGoToTheSearchPageWithTheUrlParameter(String arg0) {
        MatcherAssert.assertThat(searchPage.getCurrentUrl(), Matchers.is("https://hn.algolia.com/?q=java"));

    }


    @When("I click on the Login link")
    public void iClickOnTheLoginLink() {
        loginPage = homePage.goToLoginPage();
    }

    @Then("I should be taken to the Login Page")
    public void iShouldBeTakenToTheLoginPage() {
        MatcherAssert.assertThat(loginPage.getUrl(), Matchers.is("https://news.ycombinator.com/login?goto=news"));
    }

    @And("the login title will appear right above the login form")
    public void theLoginTitleWillAppearRightAboveTheLoginForm() {
        WebElement loginTitle = loginPage.findElement("//b");
        MatcherAssert.assertThat(loginTitle.getText(), containsString("Login"));
    }

    @When("I click on the ask link")
    public void iClickOnTheAskLink() {
        askPage = homePage.goToAskPage();
    }

    @Then("I should be taken to the ask page")
    public void iShouldBeTakenToTheAskPage() {
        MatcherAssert.assertThat(askPage.getCurrentUrl(), Matchers.is("https://news.ycombinator.com/ask"));
    }

    @And("the page title will include ask")
    public void thePageTitleWillIncludeAsk() {
        MatcherAssert.assertThat(askPage.getTitle(), containsString("Ask"));
    }

    @When("I click on the comments link")
    public void iClickOnTheCommentsLink() {
        commentsPage = homePage.goToCommentsPage();
    }

    @Then("I should be taken to the Comments Page")
    public void iShouldBeTakenToTheCommentsPage() {
        MatcherAssert.assertThat(commentsPage.getCurrentUrl(), Matchers.is("https://news.ycombinator.com/newcomments"));
    }

    @And("the page title will includes comments")
    public void thePageTitleWillIncludesComments() {
        MatcherAssert.assertThat(commentsPage.getTitle(), containsString("Comments"));
    }
}
