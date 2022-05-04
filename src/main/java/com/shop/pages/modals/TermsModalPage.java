package com.shop.pages.modals;

import com.shop.pages.CheckoutPage;
import com.shop.pages.HeaderPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

public class TermsModalPage extends HeaderPage {

    public TermsModalPage(WebDriver driver) {
        super(driver);
        WebElement element = driver.findElement(By.cssSelector("#modal"));
        PageFactory.initElements(new DefaultElementLocatorFactory(element), this);
    }

    @FindBy(css = "#modal .js-modal-content")
    private WebElement modalContent;

    @FindBy(css = "#modal .close")
    private WebElement closeButton;

    public TermsModalPage checkModalContentIsNotEmpty() {
        softAssert.assertThat(getElementText(modalContent).trim()).isNotEmpty();
        softAssert.assertAll();
        return this;
    }

    public CheckoutPage closeModal() {
        clickOnElement(closeButton);
        waitForPageLoaded();
        return new CheckoutPage(driver);
    }
}