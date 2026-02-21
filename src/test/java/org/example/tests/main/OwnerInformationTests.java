package org.example.tests.main;

import org.example.base.BaseTest;
import org.example.tests.util.page_objects.find_owner.OwnerInformation;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class OwnerInformationTests extends BaseTest {
    private OwnerInformation ownerInfo;
    private final String ownerLastName = "Kowalski";

    @BeforeMethod
    public void navigateToOwnerInformation() {
        ownerInfo = landingPage.navigateToFindOwnersPage().findOwnerAndOpen(ownerLastName);
    }

    @Test
    public void testOwnerInfoDisplayed() {
        ownerInfo.assertOwner("Name", "Jan Kowalski");
        ownerInfo.clickEditOwner();
    }
}
