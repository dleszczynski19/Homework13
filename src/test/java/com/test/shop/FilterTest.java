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

        log.info("Start checking each price in filter test");
        double[] prices = categoryStep
                .goToCategory(homePage.getArtCategory())
                .setPricesArray();

        categoryStep
                .checkIsPriceMatchWithFilter(prices[0], prices[prices.length - 1])
                .checkIsPriceMatchWithFilter(prices[0], prices[prices.length - 1] - 1)
                .checkIsPriceMatchWithFilter(prices[0] + 1, prices[prices.length - 1])
                .checkFilterTest();
        log.info(passed, passedMessage);
    }
}
