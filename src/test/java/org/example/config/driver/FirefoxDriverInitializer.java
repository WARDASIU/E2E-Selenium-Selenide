package org.example.config.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

/**
 * Creates and configures WebDriver for Firefox.
 */
public class FirefoxDriverInitializer implements DriverInitializer {

    private final FirefoxDriver firefoxDriver;

    public FirefoxDriverInitializer() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless");

        this.firefoxDriver = new FirefoxDriver(options);
    }

    @Override
    public WebDriver getDriver() {
        return firefoxDriver;
    }
}
