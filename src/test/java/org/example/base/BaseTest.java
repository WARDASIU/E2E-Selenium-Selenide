package org.example.base;

import org.example.config.driver.DriverConfig;
import org.example.config.TestConfig;
import org.example.config.enums.BrowserType;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.example.util.JavaScriptUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.example.tests.pos.page_objects.LandingPage;

import static com.codeborne.selenide.Selenide.open;

public abstract class BaseTest {
    protected final TestConfig testConfig = new TestConfig(resolveBaseUrl(), BrowserType.CHROME);
    protected final DriverConfig driverConfig = new DriverConfig();
    public LandingPage landingPage;

    @BeforeSuite
    public void setUp() {
        Configuration.baseUrl = testConfig.getBaseUrl();
        Configuration.timeout = driverConfig.getTimeoutSeconds() * 5000;
        Configuration.browser = "chrome";
        Configuration.headless = resolveHeadless();
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeTest() {
        open("");
        JavaScriptUtils.enableClickHighlight();
        landingPage = new LandingPage();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        WebDriverRunner.closeWebDriver();
    }

    private static String resolveBaseUrl() {
        String fromEnv = System.getenv("BASE_URL");
        if (fromEnv != null && !fromEnv.isBlank()) return fromEnv.trim();
        String fromProp = System.getProperty("base.url");
        if (fromProp != null && !fromProp.isBlank()) return fromProp.trim();
        return "http://localhost:8090";
    }

    private static boolean resolveHeadless() {
        String fromEnv = System.getenv("HEADLESS");
        if (fromEnv != null && !fromEnv.isBlank()) return Boolean.parseBoolean(fromEnv.trim());
        return Boolean.parseBoolean(System.getProperty("headless", "false"));
    }
}