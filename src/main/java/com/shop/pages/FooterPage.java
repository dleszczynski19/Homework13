package com.shop.pages;

import com.shop.pages.handlers.DataHandler;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FooterPage extends DataHandler {
    private static Logger log = LoggerFactory.getLogger(HomePage.class);

    public FooterPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "#link-product-page-prices-drop-1")
    private WebElement pricesDropSubMenu;

    public PricesDropPage moveToPricesDropPage(){
        scrollToElement(pricesDropSubMenu);
        clickOnElement(pricesDropSubMenu);
        return new PricesDropPage(driver);
    }
}
