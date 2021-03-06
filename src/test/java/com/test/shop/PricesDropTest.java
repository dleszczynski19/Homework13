package com.test.shop;

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
        pricesDropStep
                .goToPricesDropPage()
                .checkIsEachPricesDropProductDisplayedCorrectValue()
                .clickOnRandomProduct()
                .checkIsProductDisplayedCorrectValue()
                .assertPricesDropTest();
        log.info(passed, passedMessage);
    }
}
