package com.test.shop;

import com.configuration.TestBase;
import com.shop.steps.HeaderStep;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasketTest extends TestBase {
    private Logger log = LoggerFactory.getLogger(BasketTest.class);

    @Test
    public void shouldCheckBasketProperlyWork() {
        HeaderStep headerStep = new HeaderStep(driver, homePage);

        headerStep
                .addItemsToBasket(headerStep.parseInt(System.getProperty("basketTestItemToAdd")), true)
                .goToBasket();
        log.info("Items properly add to basket");
        headerStep
                .checkBasketItems()
                .checkBasketQuantity()
                .checkEachItemDelete();
        log.info(passed, passedMessage);
    }
}
