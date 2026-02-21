package org.example.tests.util.page_objects.find_owner;

import io.qameta.allure.Step;
import org.example.tests.util.NavigationBar;

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
    public FindOwners findOwner() {
        $x("//button[normalize-space()='Find Owner']").click();
        return this;
    }

    @Step("Search owner and open info page")
    public OwnerInformation findOwnerAndOpen(String lastName) {
        setOwnerLastName(lastName);
        findOwner();
        return new OwnerInformation();
    }

    @Step("Click add owner")
    public FindOwners addOwner() {
        $x("//a[@href='/owners/new']").click();
        return this;
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
