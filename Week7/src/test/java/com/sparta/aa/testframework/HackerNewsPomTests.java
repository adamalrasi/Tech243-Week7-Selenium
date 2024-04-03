package com.sparta.aa.testframework;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.containsString;

public class HackerNewsPomTests {
    private static final String DRIVER_LOCATION = "src/test/resources/chromedriver-win64/chromedriver.exe";

    private static final String BASE_URL = "https://news.ycombinator.com/";
    private static ChromeDriverService service;

    private WebDriver webDriver;

    public static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
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

    @BeforeEach
    public void setup() {
        webDriver = new RemoteWebDriver(service.getUrl(), getChromeOptions());
        System.out.println("🧪Start Test...");
//        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

    }

    @AfterEach
    public void afterEach() {
        webDriver.quit();
        System.out.println("🔚Finished Test case.");
    }

    @AfterAll
    static void afterAll() {
        service.stop();
        System.out.println("🔴 End of Server.");
    }

    @Test
    @DisplayName("Check that the webdriver works")
    public void checkWebDriver() {
        webDriver.get(BASE_URL);
        Assertions.assertEquals("https://news.ycombinator.com/", webDriver.getCurrentUrl());
        Assertions.assertEquals("Hacker News", webDriver.getTitle());
    }

    @Test
    @DisplayName("Check that the link to the past page works")
    public void checkPastLink() {
        // Arrange
        webDriver.get(BASE_URL);
        // Act
        WebElement pastLink = webDriver.findElement(By.linkText("past"));
        pastLink.click();
        // Assert
        MatcherAssert.assertThat(webDriver.getCurrentUrl(), Matchers.is("https://news.ycombinator.com/front"));
        MatcherAssert.assertThat(webDriver.getTitle(), containsString("front"));
    }

    @Test
    @DisplayName("Check that the link to the comments page works")
    public void checkCommentsLink(){
        // Arrange
        webDriver.get(BASE_URL);
        // Act
        WebElement commentsLink = webDriver.findElement(By.linkText("comments"));
        commentsLink.click();
        // Assert
        MatcherAssert.assertThat(webDriver.getCurrentUrl(), Matchers.is("https://news.ycombinator.com/newcomments"));
        MatcherAssert.assertThat(webDriver.getTitle(), containsString("New Comments"));
    }

    @Test
    @DisplayName("Check that the link to the ask page work")
    public void checkAskLink(){
        // Arrange
        webDriver.get(BASE_URL);
        // Act
        WebElement askLink = webDriver.findElement(By.linkText("ask"));
        askLink.click();
        // Assert
        MatcherAssert.assertThat(webDriver.getCurrentUrl(), Matchers.is("https://news.ycombinator.com/ask"));
        MatcherAssert.assertThat(webDriver.getTitle(), containsString("Ask"));
    }

    @Test
    @DisplayName("Check that we can search for java")
    void searchForJava() {
        // Arrange
        webDriver.get(BASE_URL);
        // Act
        webDriver.findElement(By.name("q")).sendKeys("java", Keys.ENTER);
        // Assert
        MatcherAssert.assertThat(webDriver.getCurrentUrl(), Matchers.is("https://hn.algolia.com/?q=java"));
    }

    @Test
    @DisplayName("Check that we can use search facility with wait")
    void searchForJavaWithWait() {
         Wait<WebDriver> webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        webDriver.get(BASE_URL);

        webDriver.findElement(By.name("q"))
                .sendKeys("Java", Keys.ENTER);

         webDriverWait.until(driver -> driver.getCurrentUrl().contains("/?q=Java"));

        MatcherAssert.assertThat(
                webDriver.findElement(By.cssSelector(".Story:nth-child(1)"))
                        .getText()
                        .toLowerCase(),
                containsString("java")
        );
    }

    @Test
    @DisplayName("Check if the navbar date on past page is correct")
    public void checkNavBarDate_On_PastPage_Correct() {
        // Arrange
        webDriver.get(BASE_URL);
        // Act
        WebElement pastLink = webDriver.findElement(By.linkText("past"));
        pastLink.click();

        WebElement navDate = webDriver.findElement(By.xpath("//font"));
        // Assert
        MatcherAssert.assertThat(webDriver.getTitle(), containsString(navDate.getText()));
    }

    @Test
    @DisplayName("Check if the date on past page is same as last posted article")
    public void checkDate_Matches_LatestArticleDate() {
        // Arrange
        webDriver.get(BASE_URL);
        // Act
        WebElement pastLink = webDriver.findElement(By.linkText("past"));
        pastLink.click();

        WebElement latestArticle = webDriver.findElement(By.className("age"));
        System.out.println(latestArticle.getAttribute("title"));
        // Assert
        MatcherAssert.assertThat(webDriver.getTitle(),
                containsString(
                        latestArticle.getAttribute("title")
                                .split("T")[0]));
    }

    @Test
    @DisplayName("Check that the past page has yesterday's date")
    void checkYesterdayDay() {
        Wait<WebDriver> wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        webDriver.get(BASE_URL);

        WebElement pastLink = wait.until(p -> p.findElement(By.linkText("past")));
        pastLink.click();

        LocalDate yesterday = LocalDate.now().minusDays(1);

        wait.until(d -> d.getCurrentUrl().contains("front"));
        String topString = webDriver.findElement(By.className("pagetop")).getText();

        MatcherAssert.assertThat(topString, containsString(yesterday.toString()));
    }

    @Test
    @DisplayName("Attempt invalid login")
    void invalidLoginTest() {
        Wait<WebDriver> wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        webDriver.get("https://news.ycombinator.com/login");

        WebElement userNameBox = wait.until(p -> p.findElement(By.name("acct")));
        userNameBox.sendKeys("Cathy");

        WebElement passwordInput = webDriver.findElement(By.name("pw"));
        // Alternative way of finding passwordInput using RelativeLocator
        // WebElement passwordInput = webDriver.findElement(
        //         RelativeLocator.with(By.tagName("input"))
        //                       .below(userNameBox));
        passwordInput.sendKeys("£&%!");

        webDriver.findElement(By.cssSelector("input[value='login']")).click();

        MatcherAssert.assertThat(
                wait.until(p -> p.findElement(By.tagName("body")).getText().contains("Bad login.")),
                Matchers.is(true)
        );
    }

    @Test
    void checkNumberOfSearchResultsPerPageIs30() {
        Wait<WebDriver> webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        webDriver.get(BASE_URL);

        webDriver.findElement(By.name("q")).sendKeys("java", Keys.ENTER);

        webDriverWait.until(d -> d.getCurrentUrl().contains("q=java"));
        List<WebElement> results = webDriver.findElements(By.className("Story"));

        MatcherAssert.assertThat(results.size(), Matchers.lessThanOrEqualTo(30));
    }

}
