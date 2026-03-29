package org.example.config.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DriverConfig {
    private long timeoutSeconds = 30;

    public long getTimeoutSeconds() {
        return timeoutSeconds;
    }

    public void setTimeoutSeconds(long timeoutSeconds) {
        this.timeoutSeconds = timeoutSeconds;
    }

    public void waitForBrowserWindow(WebDriver driver) {
        new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(d -> !d.getWindowHandles().isEmpty());
    }

    public void prepareWindowAndTimeouts(WebDriver driver) {
        waitForBrowserWindow(driver);
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(timeoutSeconds));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(timeoutSeconds));
    }
}
