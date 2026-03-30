package org.example.tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.example.base.BaseTest;
import org.testng.annotations.Test;

@Epic("Application")
@Feature("Landing Page")
public class SampleTest extends BaseTest {

    @Test
    @Story("Welcome message is displayed on the landing page")
    public void welcomePageTest() {
        landingPage.assertMainContent("Welcome");
    }
}
