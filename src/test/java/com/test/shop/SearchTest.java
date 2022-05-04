package com.test.shop;

import com.configuration.TestBase;
import com.shop.pages.SearchPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SearchTest extends TestBase {
    private Logger log = LoggerFactory.getLogger(SearchTest.class);

    @Test
    @DisplayName("Search random product")
    public void shouldSearchRandomProduct() {
        log.info("Start search random product test");
        String itemName = homePage.getRandomProductName();
        homePage.searchItem(itemName)
                .clickSearch();

        assert new SearchPage(driver).isSearchedProductExists(itemName) : "Can't find searched product";
        log.info(passed, passedMessage);
    }

    @Test
    @DisplayName("Search random product - dropdown")
    public void shouldDropdownSearchRandomProduct() {
        log.info("Start search random product test - dropdown");
        String itemName = homePage.getRandomProductName();

        assert homePage
                .searchItem(itemName)
                .isItemDisplayedInDropdown(itemName) : "Item is not displayed in dropdown";
        log.info(passed, passedMessage);
    }
}