package org.example.config.enums;

public enum BrowserType {
    CHROME("chrome"),
    FIREFOX("firefox");

    private final String selenideName;

    BrowserType(String selenideName) {
        this.selenideName = selenideName;
    }

    public String selenideName() {
        return selenideName;
    }
}
