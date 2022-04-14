package com.shop.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class SearchPage extends HeaderPage {
    private static Logger log = LoggerFactory.getLogger(SearchPage.class);
    private List<ProductPage> allProductsList = new ArrayList<>();

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "#js-product-list-header")
    private WebElement searchResultHeader;

    public boolean isSearchedProductExists(String name) {HomePage homePage = new HomePage(driver);

        if (isPageSwitched(searchResultHeader)) {
            return homePage.getAllProductsList().stream().anyMatch(p -> p.getProductName().equals(name));
        } else return false;
    }
}
