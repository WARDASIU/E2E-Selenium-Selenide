package org.example.config.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeDriverInitializer implements DriverInitializer {

    private final ChromeDriver chromeDriver;

    public ChromeDriverInitializer() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--start-maximized");

        this.chromeDriver = new ChromeDriver(options);
    }

    @Override
    public WebDriver getDriver() {
        return chromeDriver;
    }
}
