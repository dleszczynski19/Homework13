package com.test;

import com.configuration.TestBase;
import com.shop.steps.FilterStep;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilterTest extends TestBase {
    private Logger log = LoggerFactory.getLogger(FilterTest.class);

    @Test
    public void shouldCheckEachPriceFilter() {
        FilterStep filterStep = new FilterStep(driver);
        boolean isEachFilterProperly;
        int minValue = 9;
        int maxValue = 29;

        log.info("Start price filter check test");
        isEachFilterProperly = filterStep.isPriceMatchWithFilter(minValue, maxValue);
        if (isEachFilterProperly) isEachFilterProperly = filterStep.isPriceMatchWithFilter(minValue, maxValue - 1);
        if (isEachFilterProperly) isEachFilterProperly = filterStep.isPriceMatchWithFilter(minValue + 1, maxValue);
        assert isEachFilterProperly : "Filter price is not working properly";
        log.info(passed, passedMessage);
    }
}
