package com.shop.pages.configuration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PagesFactory {
    private WebDriver driver;
    private Logger logger = LoggerFactory.getLogger(PagesFactory.class);

    public PagesFactory(WebDriver driver) {
        this.driver = driver;
    }

    public <T extends BasePage> T createPage(Class<T> classToProxy) {
        logger.debug("Creating page object: {}", classToProxy.getSimpleName());
        return PageFactory.initElements(driver, classToProxy);
    }
}