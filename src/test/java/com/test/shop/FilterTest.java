package com.test.shop;

import com.configuration.TestBase;
import com.shop.steps.CategoryStep;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilterTest extends TestBase {
    private Logger log = LoggerFactory.getLogger(FilterTest.class);

    @Test
    public void shouldCheckEachPriceFilter() {
        CategoryStep categoryStep = new CategoryStep(driver, homePage);

        double[] prices = categoryStep
                .goToCategory(homePage.getArtCategory())
                .setPricesArray();
        categoryStep.checkIsPriceMatchWithFilter(prices[0], prices[prices.length - 1]);
        categoryStep.checkIsPriceMatchWithFilter(prices[0], prices[prices.length - 1] - 1);
        categoryStep.checkIsPriceMatchWithFilter(prices[0] + 1, prices[prices.length - 1]);
        log.info(passed, passedMessage);
    }
}
