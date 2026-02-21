package org.example.config;

import org.example.config.enums.BrowserType;

import static com.codeborne.selenide.Selenide.executeJavaScript;

public class TestConfig {
    private BrowserType browser = BrowserType.CHROME;
    private String baseUrl = "http://localhost:8080";

    private long driverTimeoutSeconds = 30;

    public BrowserType getBrowser() {
        return browser;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public long getDriverTimeoutSeconds() {
        return driverTimeoutSeconds;
    }

    public void enableClickHighlight() {
        executeJavaScript("""
        document.addEventListener('click', function(e) {
            const el = e.target;
            el.style.outline = '3px solid limegreen';
            el.style.backgroundColor = 'rgba(0, 255, 0, 0.25)';
            setTimeout(() => {
                el.style.outline = '';
                el.style.backgroundColor = '';
            }, 400);
        }, true);
    """);
    }
}
