package org.example.tests.util;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.example.tests.util.page_objects.find_owner.FindOwners;

import static com.codeborne.selenide.Selenide.$x;

public class NavigationBar {

    private static final String NAV_LIST_XPATH = "//ul[contains(@class,'navbar-nav')]";

    public SelenideElement navList() {
        return $x(NAV_LIST_XPATH);
    }

    /** Home link (href="/") */
    public SelenideElement linkHome() {
        return $x(NAV_LIST_XPATH + "//a[@href='/']");
    }

    /** Find Owners link (href="/owners/find") */
    public SelenideElement linkFindOwners() {
        return $x(NAV_LIST_XPATH + "//a[@href='/owners/find']");
    }

    /** Veterinarians link (href="/vets.html") */
    public SelenideElement linkVeterinarians() {
        return $x(NAV_LIST_XPATH + "//a[@href='/vets.html']");
    }

    /** Error link (href="/oups") */
    public SelenideElement linkError() {
        return $x(NAV_LIST_XPATH + "//a[@href='/oups']");
    }

    @Step("Go to Find Owners")
    public FindOwners navigateToFindOwnersPage() {
        linkFindOwners().click();
        return new FindOwners();
    }
}
