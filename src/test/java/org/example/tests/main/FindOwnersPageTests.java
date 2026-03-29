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
                .clickFindOwner()
                .assertErrorAlert(true);
    }

    @Test
    @Tag("owner")
    @Epic("FIND OWNER PAGE")
    @Feature("SEARCH RESULTS TABLE")
    public void editOwnerDataTest() {
        landingPage
                .navigateToFindOwnersPage()
                .setOwnerLastName("Kowalski")
                .clickFindOwnerWithNavigation()
                .assertOwnersTableVisible()
                .assertOwnerRowCountAtLeast(1)
                .assertOwnerRow(1, "Jan Kowalski", "21 Jump Street", "Zawoja", "1231231231");
    }
}
