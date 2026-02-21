package org.example.base;

import org.example.config.TestConfig;
import org.example.config.driver.ChromeDriverInitializer;
import org.example.config.driver.DriverInitializer;
import org.example.config.driver.FirefoxDriverInitializer;
import org.example.config.enums.BrowserType;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.example.tests.util.page_objects.LandingPage;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.open;

public abstract class BaseTest {
    private TestConfig config;
    private WebDriver driver;
    public LandingPage landingPage;

    @BeforeSuite
    public void setUp() {
        config = new TestConfig();
        Configuration.baseUrl = config.getBaseUrl();
        Configuration.timeout = config.getDriverTimeoutSeconds() * 1000;

        DriverInitializer initializer = config.getBrowser() == BrowserType.FIREFOX
                ? new FirefoxDriverInitializer()
                : new ChromeDriverInitializer();
        driver = initializer.getDriver();
        WebDriverRunner.setWebDriver(driver);

        long wait = config.getDriverTimeoutSeconds();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(wait));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(wait));
    }

    @BeforeMethod
    public void beforeTest() {
        open("");
        config.enableClickHighlight();
        landingPage = new LandingPage();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            WebDriverRunner.closeWebDriver();
        }
    }
}
