package com.shop.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class HeaderPage extends FooterPage {
    private static Logger log = LoggerFactory.getLogger(HomePage.class);
    private static final String siteTitle = "TesterSii";
    private List<WebElement> categoriesList = new ArrayList<>();
    private final String subMenuSelector = ".popover.sub-menu.js-sub-menu.collapse li";
    private int shoppingCartAmount = 0;

    public HeaderPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "#search_widget [type=\"text\"]")
    public WebElement searchInput;

    @FindBy(css = "#search_widget button")
    public WebElement searchButton;

    @FindBy(css = "#category-3")
    private WebElement clothesCategory;

    @FindBy(css = "#category-6")
    private WebElement accessoriesCategory;

    @FindBy(css = "#category-9")
    private WebElement artCategory;

    @FindBy(css = ".popover.sub-menu")
    public List<WebElement> subMenuList;

    @FindBy(css = "#_desktop_logo a")
    public WebElement homeLogo;

    @FindBy(css = "#ui-id-1 li .product")
    private List<WebElement> dropDownList;

    @FindBy(css = "#ui-id-1")
    private WebElement dropDown;

    @FindBy(css = ".cart-products-count")
    private WebElement shoppingCartItemsCount;

    public HomePage goToHomePage() {
        clickOnElement(homeLogo);
        waitForSite(siteTitle);
        return new HomePage(driver);
    }

    public boolean isItemDisplayedInDropdown(String itemName) {
        hoverElement(dropDown);
        return dropDownList.stream().anyMatch(element -> element.getText().equals(itemName));
    }

    public HeaderPage searchItem(String searchText) {
        sendKeysToElement(searchInput, searchText);
        log.info("Item \"" + searchText + "\" searched");
        return this;
    }

    public HeaderPage clickSearch() {
        clickOnElement(searchButton);
        log.info("Moved to search page");
        return this;
    }

    //region Categories
    public HeaderPage setCategoriesList() {
        categoriesList.add(clothesCategory);
        categoriesList.add(accessoriesCategory);
        categoriesList.add(artCategory);
        return this;
    }

    public boolean isSubMenuDisplayed(WebElement category) {
        if (category.getText().equals("ART")) {
            log.info("ART category don't have submenu");
            return true;
        }
        return isElementVisible(findElementInAnotherElement(category, subMenuSelector));
    }

    public HeaderPage hoverOnCategory(WebElement category) {
        hoverElement(category);
        return this;
    }

    public List<WebElement> getSubCategoriesList(WebElement category) {
        hoverElement(category);
        return findElementsInAnotherElement(category, getSubMenuSelector());
    }

    public List<WebElement> getCategoriesList() {
        return categoriesList;
    }

    public String getSubMenuSelector() {
        return subMenuSelector;
    }

    public WebElement getArtCategory() {
        return artCategory;
    }

    public HeaderPage goToCategory(WebElement category) {
        findElementInAnotherElement(category, "a").click();
        return this;
    }

    public int getShoppingCartItemsCount() {
        shoppingCartAmount = parseInt(getElementText(shoppingCartItemsCount));
        return shoppingCartAmount;
    }

    public boolean isShoppingCartUpdated(int countBefore, int addCount) {
        log.info("Items count before: " + countBefore);
        return countBefore + addCount == getShoppingCartItemsCount();
    }
    //endregion
}
