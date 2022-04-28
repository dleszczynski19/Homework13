package com.test.shop;

import com.configuration.TestBase;
import com.shop.steps.HeaderStep;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CategoriesTest extends TestBase {
    private Logger log = LoggerFactory.getLogger(CategoriesTest.class);

    @Test
    public void shouldMatchesAllCategoriesProperties() {
        HeaderStep headerStep = new HeaderStep(driver, homePage);

        headerStep
                .checkEachCategoryProperties()
                .checkEachSubcategoryProperties();
        log.info(passed, passedMessage);
    }
}
