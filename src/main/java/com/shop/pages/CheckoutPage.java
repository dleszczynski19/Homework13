package com.shop.pages;

import com.shop.pages.modals.TermsModalPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

public class CheckoutPage extends HeaderPage {
    private Logger log = LoggerFactory.getLogger(CheckoutPage.class);
    private HashMap<String, String> orderPropertyMap = new HashMap<>();

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public enum PaymentBy {
        CHECK("#payment-option-1-container label", "Payments by check", "Awaiting check payment"),
        BANK_WIRE("#payment-option-2-container label", "Bank transfer", "Awaiting bank wire payment");

        private String selector;
        private String paymentType;
        private String paymentStatus;

        PaymentBy(String selector, String paymentType, String paymentStatus) {
            this.selector = selector;
            this.paymentType = paymentType;
            this.paymentStatus = paymentStatus;
        }

        public String getSelector() {
            return selector;
        }

        public String getPaymentType() {
            return paymentType;
        }

        public String getPaymentStatus() {
            return paymentStatus;
        }
    }

    @FindBy(css = "[name=\"address1\"]")
    private WebElement addressInput;

    @FindBy(css = "[name=\"postcode\"]")
    private WebElement zipCodeInput;

    @FindBy(css = "[name=\"city\"]")
    private WebElement cityInput;

    @FindBy(css = "[name=\"id_state\"]")
    private WebElement stateSelect;

    @FindBy(css = "[name=\"id_country\"]")
    private WebElement countrySelect;

    @FindBy(css = ".js-address-form [type=\"submit\"]")
    private WebElement saveButton;

    @FindBy(css = ".row.carrier")
    private List<WebElement> shippingMethodRadioList;

    @FindBy(css = ".payment-options  .custom-radio.float-xs-left")
    private List<WebElement> paymentMethodRadioList;

    @FindBy(css = "[name=\"confirmDeliveryOption\"]")
    private WebElement confirmDeliveryButton;

    @FindBy(css = ".custom-checkbox")
    private WebElement termsOfServiceAgreeCheckbox;

    @FindBy(css = ".ps-shown-by-js button")
    private WebElement placeOrderButton;

    @FindBy(css = ".js-terms a")
    private WebElement termsOfServiceLink;

    @FindBy(css = ".cart-summary-line.cart-total .value")
    private WebElement totalPriceValue;

    public CheckoutPage fillAddress(String address) {
        orderPropertyMap.put("address", address);
        orderPropertyMap.put("date", getCurrentDate("MM/dd/yyyy"));
        sendKeysToElement(addressInput, address);
        return this;
    }

    public CheckoutPage fillZipCode(String code) {
        orderPropertyMap.put("code", code);
        sendKeysToElement(zipCodeInput, code);
        return this;
    }

    public CheckoutPage fillCity(String city) {
        orderPropertyMap.put("city", city);
        sendKeysToElement(cityInput, city);
        return this;
    }

    public CheckoutPage selectState(String state) {
        if (state != null) new Select(stateSelect).selectByVisibleText(state);
        return this;
    }

    public CheckoutPage selectCountry(String country) {
        Select countryToSelect = new Select(countrySelect);
        countryToSelect.selectByVisibleText(country);
        orderPropertyMap.put("country", getElementText(countryToSelect.getFirstSelectedOption()));
        waitForPageLoaded();
        return this;
    }

    public CheckoutPage saveDeliveryAddress() {
        clickOnElement(saveButton);
        waitForPageLoaded();
        return this;
    }

    public CheckoutPage chooseRandomShippingMethod() {
        waitForPageLoaded();
        WebElement shippingMethod = shippingMethodRadioList.get(getRandomNumber(0, shippingMethodRadioList.size() - 1));
        orderPropertyMap.put("shippingMethod", getElementText(shippingMethod));
        clickOnElement(shippingMethod);

        return this;
    }

    public CheckoutPage confirmDelivery() {
        clickOnElement(confirmDeliveryButton);
        return this;
    }

    public CheckoutPage choosePaymentMethod(PaymentBy paymentBy) {
        orderPropertyMap.put("totalPrice", getTotalPrice().toString());
        WebElement payment = findElementByCss(paymentBy.getSelector());
        orderPropertyMap.put("payment", getElementText(payment));
        orderPropertyMap.put("paymentType", paymentBy.getPaymentType());
        orderPropertyMap.put("paymentStatus", paymentBy.getPaymentStatus());
        clickOnElement(payment);
        return this;
    }

    public TermsModalPage checkTermsOfUse() {
        clickOnElement(termsOfServiceLink);
        waitForPageLoaded();
        return new TermsModalPage(driver);
    }

    public CheckoutPage agreeTermsOfUse() {
        clickOnElement(termsOfServiceAgreeCheckbox);
        return this;
    }

    public CheckoutPage placeOrder() {
        clickOnElement(placeOrderButton);
        return this;
    }

    public Double getTotalPrice() {
        return parseDouble(getElementText(totalPriceValue));
    }

    public HashMap<String, String> getOrderPropertyMap() {
        return orderPropertyMap;
    }
}