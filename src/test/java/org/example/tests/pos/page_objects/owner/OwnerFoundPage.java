package org.example.tests.pos.page_objects.owner;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThanOrEqual;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$x;

public class OwnerFoundPage extends FindOwners {

    private static final String TABLE = "//table[@id='owners']";

    private SelenideElement nameLink(int row) {
        return $x(TABLE + "/tbody/tr[" + row + "]/td[1]/a");
    }

    private SelenideElement cell(int row, int column) {
        return $x(TABLE + "/tbody/tr[" + row + "]/td[" + column + "]");
    }

    @Step("Assert owners results table is visible")
    public OwnerFoundPage assertOwnersTableVisible() {
        $x(TABLE).should(exist);
        return this;
    }

    @Step("Assert at least {minRows} row(s) in owners table")
    public OwnerFoundPage assertOwnerRowCountAtLeast(int minRows) {
        $$("#owners tbody tr").shouldHave(sizeGreaterThanOrEqual(minRows));
        return this;
    }

    @Step("Assert owner row {row}: Name={name}, Address={address}, City={city}, Telephone={telephone}")
    public OwnerFoundPage assertOwnerRow(int row, String name, String address, String city, String telephone) {
        nameLink(row).shouldHave(exactText(name));
        cell(row, 2).shouldHave(exactText(address));
        cell(row, 3).shouldHave(exactText(city));
        cell(row, 4).shouldHave(exactText(telephone));
        return this;
    }

    @Step("Open owner from results row {row}")
    public OwnerInformation clickOwnerRow(int row) {
        nameLink(row).click();
        return new OwnerInformation();
    }

    public String getOwnerName(int row) {
        return nameLink(row).getText();
    }

    public String getOwnerAddress(int row) {
        return cell(row, 2).getText();
    }

    public String getOwnerCity(int row) {
        return cell(row, 3).getText();
    }

    public String getOwnerTelephone(int row) {
        return cell(row, 4).getText();
    }

    public String getPetsCellText(int row) {
        return cell(row, 5).getText();
    }
}
