package org.example.tests.pos;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.example.tests.pos.page_objects.owner.FindOwners;

import static com.codeborne.selenide.Selenide.$x;

public class NavigationBar {

    private static final String NAV_LIST_XPATH = "//ul[contains(@class,'navbar-nav')]";

    public SelenideElement linkHome() {
        return $x(NAV_LIST_XPATH + "//a[@href='/']");
    }

    public SelenideElement linkFindOwners() {
        return $x(NAV_LIST_XPATH + "//a[@href='/owners/find']");
    }

    public SelenideElement linkVeterinarians() {
        return $x(NAV_LIST_XPATH + "//a[@href='/vets.html']");
    }

    public SelenideElement linkError() {
        return $x(NAV_LIST_XPATH + "//a[@href='/oups']");
    }

    @Step("Go to Find Owners")
    public FindOwners navigateToFindOwnersPage() {
        linkFindOwners().click();
        return new FindOwners();
    }
}
