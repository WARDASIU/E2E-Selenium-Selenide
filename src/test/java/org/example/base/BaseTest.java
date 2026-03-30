package org.example.base;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Allure;
import org.example.config.TestConfig;
import org.example.config.driver.DriverConfig;
import org.example.config.enums.BrowserType;
import org.example.tests.pos.page_objects.LandingPage;
import org.example.util.JavaScriptUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import static com.codeborne.selenide.Selenide.open;

public abstract class BaseTest {
    protected static final TestConfig LOCAL_CONFIG =
            new TestConfig("http://localhost:8090", BrowserType.CHROME);

    protected final DriverConfig driverConfig = new DriverConfig();
    public LandingPage landingPage;

    @BeforeTest
    public void setUp() {
        String browser   = System.getProperty("browser");
        String remoteUrl = System.getProperty("remoteUrl");
        boolean isRemote = remoteUrl != null && !remoteUrl.isBlank();

        Configuration.baseUrl  = isRemote ? TestConfig.resolveBaseUrl() : LOCAL_CONFIG.getBaseUrl();
        Configuration.browser  = (browser != null && !browser.isBlank())
                ? browser
                : LOCAL_CONFIG.getBrowser().selenideName();
        Configuration.headless = TestConfig.resolveHeadless();
        Configuration.timeout  = driverConfig.getTimeoutSeconds() * 1000;

        if (isRemote) {
            Configuration.remote                  = remoteUrl;
            Configuration.remoteConnectionTimeout = 120_000;
            Configuration.remoteReadTimeout       = 120_000;
        } else {
            Configuration.remote = null;
        }
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeTest() {
        Allure.label("browser", Configuration.browser);
        open("");
        JavaScriptUtils.enableClickHighlight();
        landingPage = new LandingPage();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        WebDriverRunner.closeWebDriver();
    }
}
