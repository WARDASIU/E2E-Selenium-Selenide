package org.example.tests.pos.page_objects;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.example.tests.pos.NavigationBar;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$x;

public class LandingPage extends NavigationBar {
    @Step("Get main content")
    public LandingPage assertMainContent(String expectedText) {
        SelenideElement mainContent = $x("//div[contains(@class,'xd-container')]");

        mainContent.should(exist);
        mainContent.find(By.tagName("h2")).shouldHave(text(expectedText));
        return this;
    }
}
