package org.example.base;

import org.example.config.driver.DriverConfig;
import org.example.config.TestConfig;
import org.example.config.driver.ChromeDriverInitializer;
import org.example.config.driver.DriverInitializer;
import org.example.config.driver.FirefoxDriverInitializer;
import org.example.config.enums.BrowserType;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.example.util.JavaScriptUtils;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.example.tests.pos.page_objects.LandingPage;

import static com.codeborne.selenide.Selenide.open;

public abstract class BaseTest {
    protected final TestConfig testConfig = new TestConfig(resolveBaseUrl(), BrowserType.CHROME);
    protected final DriverConfig driverConfig = new DriverConfig();
    private WebDriver driver;
    public LandingPage landingPage;

    @BeforeSuite
    public void setUp() {
        Configuration.baseUrl = testConfig.getBaseUrl();
        Configuration.timeout = driverConfig.getTimeoutSeconds() * 5000;

        DriverInitializer initializer = testConfig.getBrowser() == BrowserType.FIREFOX
                ? new FirefoxDriverInitializer()
                : new ChromeDriverInitializer();
        driver = initializer.getDriver();
        WebDriverRunner.setWebDriver(driver);
        driverConfig.prepareWindowAndTimeouts(driver);
    }

    @BeforeMethod
    public void beforeTest() {
        open("");
        JavaScriptUtils.enableClickHighlight();
        landingPage = new LandingPage();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            WebDriverRunner.closeWebDriver();
        }
    }

    private static String resolveBaseUrl() {
        String fromEnv = System.getenv("BASE_URL");
        if (fromEnv != null && !fromEnv.isBlank()) {
            return fromEnv.trim();
        }
        String fromProp = System.getProperty("base.url");
        if (fromProp != null && !fromProp.isBlank()) {
            return fromProp.trim();
        }
        return "http://localhost:8090";
    }
}
