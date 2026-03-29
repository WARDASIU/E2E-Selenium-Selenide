package org.example.tests.pos.page_objects.owner;

import io.qameta.allure.Step;
import org.example.tests.main.FindOwnersPageTests;
import org.example.tests.pos.NavigationBar;
import org.example.tests.pos.page_objects.owner.add_owner.AddOwnerPage;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class FindOwners extends NavigationBar {
    @Step("Set owner last name")
    public FindOwners setOwnerLastName(String lastName) {
        $("#lastName").setValue(lastName);
        return this;
    }

    @Step("Click find owner")
    public FindOwners clickFindOwner() {
        $x("//button[normalize-space()='Find Owner']").click();

        return new FindOwners();
    }

    @Step("Click find owner")
    public OwnerFoundPage clickFindOwnerWithNavigation() {
        $x("//button[normalize-space()='Find Owner']").click();

        return new OwnerFoundPage();
    }

    @Step("Click add owner")
    public AddOwnerPage clickAddOwner() {
        $x("//a[@href='/owners/new']").click();

        return new AddOwnerPage();
    }

    @Step("Check if error alert is shown")
    public FindOwners assertErrorAlert(boolean expectedExistence){
        if (expectedExistence){
            $x("//p[normalize-space()='has not been found']").should(exist);
        }else {
            $x("//p[normalize-space()='has not been found']").should(not(exist));
        }

        return this;
    }
}
