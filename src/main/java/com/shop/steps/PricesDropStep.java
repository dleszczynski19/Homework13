package com.shop.steps;

import com.shop.pages.FooterPage;
import com.shop.pages.HeaderPage;
import com.shop.pages.PricesDropPage;
import com.shop.pages.ProductPage;
import com.shop.pages.models.ProductModel;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class PricesDropStep extends HeaderPage {
    private Logger log = LoggerFactory.getLogger(PricesDropStep.class);
    private FooterPage footerPage = new FooterPage(driver);
    private PricesDropPage pricesDropPage = new PricesDropPage(driver);
    private String productName;
    private List<ProductModel> allProductsList = new ArrayList<>();

    public PricesDropStep(WebDriver driver) {
        super(driver);
    }

    public PricesDropStep goToPricesDropPage() {
        footerPage
                .moveToPricesDropPage()
                .isCorrectPageLoaded(pricesDropPage.getPagePath());
        allProductsList = pricesDropPage
                .setProductList()
                .getAllProductsList();
        log.info("Moved to Prices drop page");
        return this;
    }

    public boolean isEachPricesDropProductDisplayedCorrectValue() {
        boolean isTrue;
        for (ProductModel product : allProductsList) {
            isTrue = pricesDropPage.isPricesDropProductDisplayedCorrectValue(product);
            if (!isTrue) {
                log.error("Product values are not properly");
                return false;
            }
        }
        return true;
    }

    public ProductSteps clickOnRandomProduct() {
        ProductModel productModel = allProductsList.get(getRandomNumber(0, allProductsList.size() - 1));
        productName = productModel.getProductName();
        clickOnElement(productModel.getProductLink());
        log.info("Click on product: " + productName);
        return new ProductSteps(driver, productName, new ProductPage(driver));
    }
}
