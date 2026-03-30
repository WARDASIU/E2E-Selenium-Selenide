package org.example.tests.main;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.example.base.BaseTest;
import org.example.tests.pos.model.OwnerFormData;
import org.example.tests.pos.page_objects.owner.add_owner.AddOwnerPage;
import org.example.tests.pos.page_objects.owner.add_owner.dataprovider.AddOwnerDataprovider;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Owner Management")
@Feature("Add Owner")
public class AddOwnerPageTests extends BaseTest {

    private AddOwnerPage addOwnerPage;

    @BeforeMethod
    public void navigateToAddOwnerForm() {
        addOwnerPage = landingPage
                .navigateToFindOwnersPage()
                .clickAddOwner();
    }

    @Test
    @Story("Add owner form is visible after navigation")
    public void testAddOwnerFormVisible() {
        addOwnerPage.assertAddOwnerFormVisible();
    }

    @Test(dataProvider = "ownerFormData",
            dataProviderClass = AddOwnerDataprovider.class)
    @Story("Owner can be added with valid form data")
    public void testAddOwnerWithOwnerFormData(OwnerFormData data) {
        addOwnerPage
                .assertAddOwnerFormVisible()
                .fillOwnerForm(data)
                .clickAddOwnerButton()
                .assertOwner("Name", data.getFirstName() + " " + data.getLastName())
                .assertOwner("Address", data.getAddress())
                .assertOwner("City", data.getCity())
                .assertOwner("Telephone", data.getTelephone());
    }
}
