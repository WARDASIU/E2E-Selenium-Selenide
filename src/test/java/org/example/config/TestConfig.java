package org.example.config;

import org.example.config.enums.BrowserType;


public class TestConfig {
    private BrowserType browser;
    private String baseUrl;

    private long driverTimeoutSeconds = 30;

    public TestConfig(String baseUrl, BrowserType browser){
        this.baseUrl = baseUrl;
        this.browser = browser;
    }

    public BrowserType getBrowser() {
        return browser;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public long getDriverTimeoutSeconds() {
        return driverTimeoutSeconds;
    }
}
