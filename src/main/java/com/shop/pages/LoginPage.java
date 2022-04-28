package com.shop.pages;

import com.shop.pages.helpers.WebElementHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends WebElementHelper {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = ".no-account a")
    private WebElement registrationButton;

    @FindBy(css = ".form-control[type=\"email\"]")
    private WebElement emailInput;

    @FindBy(css = "[type=\"password\"]")
    private WebElement passwordInput;

    @FindBy(css = "#submit-login")
    private WebElement signInButton;

    public RegistrationPage goToRegistration() {
        clickOnElement(registrationButton);
        waitForPageLoaded();
        return new RegistrationPage(driver);
    }

    public LoginPage fillEmail(String email) {
        sendKeysToElement(emailInput, email);
        return this;
    }

    public LoginPage fillPassword(String password) {
        sendKeysToElement(emailInput, password);
        return this;
    }
}
