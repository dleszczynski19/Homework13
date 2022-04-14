package com.test;

import com.configuration.TestBase;
import com.shop.pages.HomePage;
import com.shop.pages.SearchPage;
import com.shop.pages.handlers.DataHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SearchTest extends TestBase {
    private Logger log = LoggerFactory.getLogger(SearchTest.class);

    @Test
    @DisplayName("Search random product")
    public void shouldSearchRandomProduct() {
        HomePage homePage = new HomePage(driver);
        SearchPage searchPage = new SearchPage(driver);

        log.info("Start search random product test");
        String itemName = homePage.getRandomProductName();
        homePage.searchItem(itemName)
                .clickSearch();
        assert searchPage.isSearchedProductExists(itemName) : "Can't find searched product";
        log.info(passed, passedMessage);
    }

    @Test
    @DisplayName("Search random product - dropdown")
    public void shouldDropdownSearchRandomProduct() {
        HomePage homePage = new HomePage(driver);
        SearchPage searchPage = new SearchPage(driver);
        String itemName = homePage.getRandomProductName();

        log.info("Start search random product test - dropdown");
        DataHandler dataHandler = new DataHandler(driver);
        dataHandler.getRandomSocialTitle();
        homePage.searchItem(itemName);
        assert searchPage.isSearchedProductExists(itemName) : "Can't find searched product";
        log.info(passed, passedMessage);
    }
}