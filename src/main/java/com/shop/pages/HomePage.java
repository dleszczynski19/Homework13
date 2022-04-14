package com.shop.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends HeaderPage {
    private static Logger log = LoggerFactory.getLogger(HomePage.class);
    private List<ProductPage> allProductsList = new ArrayList<>();
    private ProductPage currentProduct;

    public HomePage(WebDriver driver) {
        super(driver);
        setAllProducts();
    }

    @FindBy(css = "#main [itemprop=\"url\"]")
    private List<WebElement> popularProductsList;

    @FindBy(css = "#main [itemprop=\"itemListElement\"]")
    private List<WebElement> productsList;

    //region Search
    public HomePage searchItem(String searchText) {
        sendKeys(searchInput, searchText);
        log.info("Item \"" + searchText + "\" searched");
        return this;
    }

    public HomePage clickSearch() {
        click(searchButton);
        log.info("Moved to search page");
        return this;
    }
    //endregion

    //region Products
    public String getRandomProductName() {
        waitForElement(productsList.get(0));
        currentProduct = allProductsList.get(getRandomNumber(0, allProductsList.size()) - 1);
        String productName = currentProduct.getProductName();
        log.info("Current product name set to: " + productName);
        return productName;
    }


    public HomePage setAllProducts() {
        waitForElement(productsList.get(0));
        for (WebElement product : productsList) {
            allProductsList.add(new ProductPage(driver, product));
        }
        log.info("All products on page set");
        return this;
    }

    public List<ProductPage> getAllProductsList() {
        return allProductsList;
    }

    public ProductPage getCurrentProduct() {
        return currentProduct;
    }
    //endregion

}