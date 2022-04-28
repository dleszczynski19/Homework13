package com.shop.pages;

import com.shop.pages.models.ProductModel;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends HeaderPage {
    private static Logger log = LoggerFactory.getLogger(HomePage.class);
    private List<ProductModel> allProductsList;
    private ProductModel currentProduct;

    public HomePage(WebDriver driver) {
        super(driver);
        setAllProducts();
    }

    @FindBy(css = "#main [itemprop=\"url\"]")
    private List<WebElement> popularProductsList;

    @FindBy(css = "#main [itemprop=\"itemListElement\"]")
    private List<WebElement> productsList;

    //region Products
    public String getRandomProductName() {
        waitForElement(productsList.get(1));
        currentProduct = allProductsList.get(getRandomNumber(0, allProductsList.size() - 1));
        String productName = currentProduct.getProductName();
        log.info("Current product name set to: " + productName);
        return productName;
    }

    public HomePage clickOnProduct(WebElement product) {
        clickOnElement(product);
        return this;
    }

    public WebElement getRandomProduct() {
        waitForElement(productsList.get(1));
        currentProduct = allProductsList.get(getRandomNumber(0, allProductsList.size() - 1));
        return currentProduct.getProductLink();
    }

    public HomePage setAllProducts() {
        allProductsList = new ArrayList<>();
        for (WebElement product : productsList) {
            allProductsList.add(new ProductModel(driver, product));
        }
        log.info("All products on page set");
        return this;
    }

    public List<ProductModel> getAllProductsList() {
        return allProductsList;
    }

    public ProductModel getCurrentProduct() {
        return currentProduct;
    }

    public List<WebElement> getProductsList() {
        return productsList;
    }
    //endregion
}
