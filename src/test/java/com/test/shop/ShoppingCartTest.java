package com.test.shop;

import com.configuration.TestBase;
import com.shop.steps.HeaderStep;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShoppingCartTest extends TestBase {
    private Logger log = LoggerFactory.getLogger(CategoriesTest.class);

    @Test
    public void isProductSuccessfullyAddedToCart() {
        log.info("Start successfully added item to cart test");
        for (int i = 0; i < homePage.parseInt(System.getProperty("shoppingCartTestRepetitionsNumber")); i++) {
            assert new HeaderStep(driver, homePage)
                    .clickOnRandomItem()
                    .isItemProperlyAddToCart() : "Item is not properly added to cart";
            homePage.goToHomePage();
        }
        log.info(passed, passedMessage);
    }
}
