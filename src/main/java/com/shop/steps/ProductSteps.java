package com.shop.steps;

import com.shop.pages.HeaderPage;
import com.shop.pages.HomePage;
import com.shop.pages.ProductPage;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductSteps extends HeaderPage {
    private static Logger log = LoggerFactory.getLogger(ProductSteps.class);
    private String productName;
    private HomePage homePage;
    private ProductPage productPage;

    public ProductSteps(WebDriver driver, ProductPage productPage, HomePage homePage) {
        super(driver);
        this.homePage = homePage;
        this.productPage = productPage;
    }

    public ProductSteps(WebDriver driver, String productName, ProductPage productPage) {
        super(driver);
        this.productName = productName;
        homePage = new HomePage(driver);
        this.productPage = productPage;
    }

    public boolean isProductDisplayedCorrectValue() {
        if (isProductLoaded(productName)) {
            ProductPage productPage = new ProductPage(driver, productName);
            return productPage.isCorrectLabelDisplayed(System.getProperty("discountLabel")) &&
                    productPage.isCorrectDiscount(System.getProperty("discountValue"));
        } else {
            log.error("Page is not loaded properly");
            return false;
        }
    }

    public boolean isItemProperlyAdded() {
        int shoppingCartAmount = getShoppingCartItemsCount();
        int quantityToSet = getRandomNumber(1, 5);
        return productPage
                .setCustomizableMessage()
                .setQuantityValue(quantityToSet)
                .createItemModel()
                .addItemToCart()
                .isProperlyItemAdd() && isShoppingCartUpdated(shoppingCartAmount, quantityToSet);

    }
}
