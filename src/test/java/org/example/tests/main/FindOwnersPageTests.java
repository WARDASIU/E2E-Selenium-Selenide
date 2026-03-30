package org.example.tests.main;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.example.base.BaseTest;
import org.testng.annotations.Test;

@Epic("Owner Management")
@Feature("Find Owners")
public class FindOwnersPageTests extends BaseTest {

    @Test
    @Story("Search with invalid last name shows an error alert")
    public void testInvalidLastname() {
        landingPage
                .navigateToFindOwnersPage()
                .setOwnerLastName("INCORRECT_NAME")
                .clickFindOwner()
                .assertErrorAlert(true);
    }

    @Test
    @Story("Search by last name returns matching owners in a table")
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
