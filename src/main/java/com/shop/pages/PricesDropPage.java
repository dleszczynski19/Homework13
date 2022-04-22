package com.shop.pages;

import com.shop.pages.models.ProductModel;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class PricesDropPage extends HeaderPage {
    private static Logger log = LoggerFactory.getLogger(PricesDropPage.class);
    private final String pagePath = "Home On sale";
    private List<ProductModel> allProductsList = new ArrayList<>();

    public PricesDropPage(WebDriver driver) {
        super(driver);
    }

    public String getPagePath() {
        return pagePath;
    }

    public PricesDropPage setProductList() {
        HomePage homePage = new HomePage(driver);
        allProductsList = homePage.getAllProductsList();
        log.info("Prices drop products list set");
        return this;
    }

    public boolean isPricesDropProductDisplayedCorrectValue(ProductModel product) {
        return product.isCorrectProductDiscount() && product.isDiscountDisplayed() &&
                product.isRegularPriceDisplayed() && product.isCorrectDiscount(System.getProperty("discountValue"));
    }

    public List<ProductModel> getAllProductsList() {
        return allProductsList;
    }
}
