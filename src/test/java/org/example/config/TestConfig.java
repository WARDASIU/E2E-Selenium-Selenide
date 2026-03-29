package org.example.config;

import org.example.config.enums.BrowserType;

public class TestConfig {
    private final String baseUrl;
    private final BrowserType browser;

    public TestConfig(String baseUrl, BrowserType browser) {
        this.baseUrl = baseUrl;
        this.browser = browser;
    }

    public BrowserType getBrowser() {
        return browser;
    }

    public String getBaseUrl() {
        return baseUrl;
    }
}
