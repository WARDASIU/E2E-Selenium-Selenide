package org.example.tests;

import org.example.base.BaseTest;
import org.testng.annotations.Test;

public class SampleTest extends BaseTest {
    @Test
    public void welcomePageTest(){
        landingPage.assertMainContent("Welcome");
    }
}
