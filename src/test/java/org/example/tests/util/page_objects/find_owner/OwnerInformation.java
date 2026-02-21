package org.example.tests.util.page_objects.find_owner;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$x;

public class OwnerInformation {

    private static final String OWNER = "//h2[text()='Owner Information']/following-sibling::table[1]";
    private static final String PETS = "//h2[text()='Pets and Visits']/following-sibling::table[1]";

    private SelenideElement ownerCell(String label) {
        return $x(OWNER + "//th[text()='" + label + "']/following-sibling::td");
    }

    private SelenideElement petField(int row, String label) {
        return $x(PETS + "//tr[" + row + "]//dt[text()='" + label + "']/following-sibling::dd[1]");
    }

    public String getOwnerName() { return ownerCell("Name").getText(); }
    public String getOwnerAddress() { return ownerCell("Address").getText(); }
    public String getOwnerCity() { return ownerCell("City").getText(); }
    public String getOwnerTelephone() { return ownerCell("Telephone").getText(); }

    public String getPetName(int row) { return petField(row, "Name").getText(); }
    public String getPetBirthDate(int row) { return petField(row, "Birth Date").getText(); }
    public String getPetType(int row) { return petField(row, "Type").getText(); }

    @Step("Assert owner: {label}={expected}")
    public OwnerInformation assertOwner(String label, String expected) {
        ownerCell(label).shouldHave(exactText(expected));
        return this;
    }

    @Step("Click Edit Owner")
    public OwnerInformation clickEditOwner() {
        $x("//a[text()='Edit Owner']").click();
        return this;
    }

    @Step("Click Add New Pet")
    public OwnerInformation clickAddNewPet() {
        $x("//a[text()='Add New Pet']").click();
        return this;
    }

    @Step("Click Edit Pet")
    public OwnerInformation clickEditPet(int row) {
        $x(PETS + "//tr[" + row + "]//a[text()='Edit Pet']").click();
        return this;
    }

    @Step("Click Add Visit")
    public OwnerInformation clickAddVisit(int row) {
        $x(PETS + "//tr[" + row + "]//a[text()='Add Visit']").click();
        return this;
    }
}
