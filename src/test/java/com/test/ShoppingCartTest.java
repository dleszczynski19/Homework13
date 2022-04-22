package com.test;

import com.configuration.TestBase;
import com.shop.steps.HeaderStep;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShoppingCartTest extends TestBase {
    private Logger log = LoggerFactory.getLogger(CategoriesTest.class);

    @Test
    public void isProductSuccessfullyAddedToCart() {
        HeaderStep headerStep = new HeaderStep(driver, homePage);

        for (int i = 0; i < 3; i++) {
            headerStep.addRandomItemToCart();
        }
    }
}
