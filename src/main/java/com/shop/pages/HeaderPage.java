package com.shop.pages;

import com.shop.pages.handlers.DataHandler;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class HeaderPage extends DataHandler {
    private static Logger log = LoggerFactory.getLogger(HomePage.class);

    public HeaderPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "#search_widget [type=\"text\"]")
    public WebElement searchInput;

    @FindBy(css = "#search_widget button")
    public WebElement searchButton;

    @FindBy(css = ".dropdown-item[data-depth=\"0\"]")
    public List<WebElement> categoriesList;

    @FindBy(css = ".dropdown-item.dropdown-submenu")
    public List<WebElement> subCategoriesList;

    @FindBy(css = ".popover.sub-menu")
    public List<WebElement> subMenuList;

    //region Categories
    public HeaderPage hoverOnCategory(int index) {
        hoverOnElement(categoriesList.get(index));
        return this;
    }

    public HeaderPage hoverOnCategory(String text) {
        hoverOnElement(findElementByName(categoriesList, text));
        return this;
    }

    public String getCategoryName(int index) {
        return getElementNameFromList(categoriesList, index);
    }

    public String getCategoryName(String name) {
        return getElementNameFromList(categoriesList, name);
    }

    public String getSubCategoryName(int index) {
        return getElementNameFromList(subCategoriesList, index);
    }

    public String getSubCategoryName(String name) {
        return getElementNameFromList(subCategoriesList, name);
    }
    //endregion
}
