package com.shop.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class RegistrationPage extends HeaderPage {
    private static Logger log = LoggerFactory.getLogger(RegistrationPage.class);
    private WebElement offersCheckbox;
    private WebElement customerPrivacyCheckbox;
    private WebElement newsletterCheckbox;
    private WebElement statuteCheckbox;

    public RegistrationPage(WebDriver driver) {
        super(driver);
        offersCheckbox = findElementByElementInside(checkboxList, "input", "name", "optin");
        customerPrivacyCheckbox = findElementByElementInside(checkboxList, "input", "name", "customer_privacy");
        newsletterCheckbox = findElementByElementInside(checkboxList, "input", "name", "newsletter");
        statuteCheckbox = findElementByElementInside(checkboxList, "input", "name", "psgdpr");
    }

    public enum GenderTitle {
        MR("1"), MRS("2");

        private String value;

        GenderTitle(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    @FindBy(css = ".custom-radio")
    private List<WebElement> titleRadio;

    @FindBy(css = "[name=\"firstname\"]")
    private WebElement firstNameInput;

    @FindBy(css = "[name=\"lastname\"]")
    private WebElement lastNameInput;

    @FindBy(css = ".register-form [name=\"email\"]")
    private WebElement emailInput;

    @FindBy(css = "[name=\"password\"]")
    private WebElement passwordInput;

    @FindBy(css = "[name=\"birthday\"]")
    private WebElement birthdayInput;

    @FindBy(css = ".custom-checkbox")
    private List<WebElement> checkboxList;

    @FindBy(css = ".register-form [type=\"submit\"]")
    private WebElement saveButton;

    public RegistrationPage chooseGenderTitle(GenderTitle genderTitle) {
        clickOnElement(findElementByElementInside(titleRadio, "input", "value", genderTitle.getValue()));
        return this;
    }

    public RegistrationPage fillFirstName(String firstName) {
        sendKeysToElement(firstNameInput, firstName);
        return this;
    }

    public RegistrationPage fillLastName(String lastName) {
        sendKeysToElement(lastNameInput, lastName);
        return this;
    }

    public RegistrationPage fillEmail(String email) {
        sendKeysToElement(emailInput, email);
        return this;
    }

    public RegistrationPage fillPassword(String password) {
        sendKeysToElement(passwordInput, password);
        return this;
    }

    public RegistrationPage fillBirthday(String day, String month, String year) {
        sendKeysToElement(birthdayInput, month + "/" + day + "/" + year);
        return this;
    }

    public RegistrationPage acceptOffers(boolean isAccept) {
        if (isAccept) clickOnElement(offersCheckbox);
        return this;
    }

    public RegistrationPage acceptPrivacy(boolean isAccept) {
        if (isAccept) clickOnElement(customerPrivacyCheckbox);
        return this;
    }

    public RegistrationPage acceptNewsletter(boolean isAccept) {
        if (isAccept) clickOnElement(newsletterCheckbox);
        return this;
    }

    public RegistrationPage acceptStatute(boolean isAccept) {
        if (isAccept) clickOnElement(statuteCheckbox);
        return this;
    }

    public HomePage clickOnSaveButton() {
        clickOnElement(saveButton);
        waitForPageLoaded();
        log.info("User properly registered");
        return new HomePage(driver);
    }
}