package com.test;

import com.configuration.TestBase;
import com.shop.steps.PricesDropStep;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PricesDropTest extends TestBase {
    private Logger log = LoggerFactory.getLogger(SearchTest.class);

    @Test
    public void shouldCheckPricesDrop() {
        PricesDropStep pricesDropStep = new PricesDropStep(driver);

        log.info("Start prices drop check test");
        boolean firstCondition = pricesDropStep
                .goToPricesDropPage()
                .isEachPricesDropProductDisplayedCorrectValue();
        log.info("First condition is: " + firstCondition);
        boolean secondCondition = pricesDropStep
                .clickOnRandomProduct()
                .isProductDisplayedCorrectValue();
        log.info("Second condition is: " + secondCondition);
        assert firstCondition && secondCondition;
        log.info(passed, passedMessage);
    }
}
