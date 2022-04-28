package com.shop.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccountPage extends HeaderPage {

    public MyAccountPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "#history-link")
    private WebElement orderHistoryLink;

    public MyAccountPage goToOrderHistory(){
        clickOnElement(orderHistoryLink);
        waitForPageLoaded();
        return this;
    }
}
