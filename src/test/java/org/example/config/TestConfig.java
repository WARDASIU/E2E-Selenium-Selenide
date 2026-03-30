package org.example.config;

import org.example.config.enums.BrowserType;

public class TestConfig {
    private final BrowserType browser;
    private final String baseUrl;

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

    public static String resolveBaseUrl() {
        String fromEnv = System.getenv("BASE_URL");
        if (fromEnv != null && !fromEnv.isBlank()) return fromEnv.trim();
        String fromProp = System.getProperty("base.url");
        if (fromProp != null && !fromProp.isBlank()) return fromProp.trim();
        return "http://localhost:8090";
    }

    public static boolean resolveHeadless() {
        String fromEnv = System.getenv("HEADLESS");
        if (fromEnv != null && !fromEnv.isBlank()) return Boolean.parseBoolean(fromEnv.trim());
        return Boolean.parseBoolean(System.getProperty("headless", "false"));
    }
}
