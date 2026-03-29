package org.example.tests.pos.page_objects.owner.add_owner;

import io.qameta.allure.Step;
import org.example.tests.pos.model.OwnerFormData;
import org.example.tests.pos.page_objects.owner.FindOwners;
import org.example.tests.pos.page_objects.owner.OwnerInformation;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class AddOwnerPage extends FindOwners {
    @Step("Set first name")
    public AddOwnerPage setFirstName(String firstName) {
        $("#firstName").setValue(firstName);
        return this;
    }

    @Step("Set last name")
    public AddOwnerPage setLastName(String lastName) {
        $("#lastName").setValue(lastName);
        return this;
    }

    @Step("Set address")
    public AddOwnerPage setAddress(String address) {
        $("#address").setValue(address);
        return this;
    }

    @Step("Set city")
    public AddOwnerPage setCity(String city) {
        $("#city").setValue(city);
        return this;
    }

    @Step("Set telephone")
    public AddOwnerPage setTelephone(String telephone) {
        $("#telephone").setValue(telephone);
        return this;
    }

    @Step("Fill add-owner form from data")
    public AddOwnerPage fillOwnerForm(OwnerFormData data) {
        setFirstName(data.getFirstName());
        setLastName(data.getLastName());
        setAddress(data.getAddress());
        setCity(data.getCity());
        setTelephone(data.getTelephone());
        return this;
    }

    @Step("Click Add Owner button (submit form)")
    public OwnerInformation clickAddOwnerButton() {
        $x("//form[@id='add-owner-form']//button[@type='submit' and normalize-space()='Add Owner']").click();
        return new OwnerInformation();
    }


    @Step("Assert add owner form is visible")
    public AddOwnerPage assertAddOwnerFormVisible() {
        $x("//h2[normalize-space()='Owner']").should(exist);
        $("#add-owner-form").should(exist);
        return this;
    }
}
