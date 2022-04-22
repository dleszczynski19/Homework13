package com.shop.pages.configuration.userConfiguration;

import com.shop.pages.configuration.BasePage;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageFactory extends BasePage {
    private Logger log = LoggerFactory.getLogger(PageFactory.class);

    public <T extends BasePage> T at(Class<T> pageType) {
        log.debug("Initializing " + pageType.getSimpleName() + " object..");
        return org.openqa.selenium.support.PageFactory.initElements(driver, pageType);
    }
}
