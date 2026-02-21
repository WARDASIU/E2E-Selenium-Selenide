package org.example.tests.main;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.testng.Tag;
import org.example.base.BaseTest;
import org.testng.annotations.Test;

public class FindOwnersPageTests extends BaseTest {
    @Test
    @Tag("owner")
    @Epic("FIND OWNER PAGE")
    @Feature("INVALID LAST NAME TEST")
    public void testInvalidLastname(){
        landingPage
                .navigateToFindOwnersPage()
                .setOwnerLastName("INCORRECT_NAME")
                .findOwner()
                .assertErrorAlert(true);
    }
}
