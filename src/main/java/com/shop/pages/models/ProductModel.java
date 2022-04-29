package com.shop.pages.models;

import com.shop.pages.handlers.DataHandler;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductModel extends DataHandler {
    private Logger log = LoggerFactory.getLogger(ProductModel.class);
    private WebElement product;

    public ProductModel(WebDriver driver, WebElement product) {
        super(driver);
        this.product = product;
        PageFactory.initElements(new DefaultElementLocatorFactory(product), this);
    }

    @FindBy(css = "[itemprop=\"url\"]")
    private WebElement productName;

    @FindBy(css = ".price")
    private WebElement productPrice;

    @FindBy(css = ".regular-price")
    private WebElement regularPrice;

    @FindBy(css = "img")
    private WebElement productImage;

    @FindBy(css = ".product-flag.discount")
    private WebElement productDiscount;

    @FindBy(css = "a")
    private WebElement productLink;

    public String getProductName() {
        return productName.getText();
    }

    public double getProductPrice() {
        return parseDouble(productPrice.getText());
    }

    public Double getRegularPrice() {
        try {
            return parseDouble(regularPrice.getText());
        } catch (Exception e) {
            log.info("Item don't have regular price");
            return null;
        }
    }

    public String getCurrency() {
        return productPrice.getText().replaceAll("[0-9.]", "");
    }

    public String getProductImage() {
        return productImage.getAttribute("src");
    }

    public WebElement getProductWebElement() {
        return product;
    }

    public String getProductDiscount() {
        try {
            return productDiscount.getText();
        } catch (Exception e) {
            log.info("Item don't have discount");
            return null;
        }
    }

    public Double getProductDiscountValue() {
        try {
            return Double.parseDouble(productDiscount.getText().replaceAll("[^0-9]", ""));
        } catch (Exception e) {
            log.info("Item don't have discount");
            return null;
        }
    }

    public WebElement getRegularPriceWebElement() {
        return regularPrice;
    }

    public WebElement getDiscountWebElement() {
        return productDiscount;
    }

    public WebElement getProductLink() {
        return productLink;
    }

    public boolean isCorrectProductDiscount() {
        return getProductDiscount().equals(System.getProperty("discount"));
    }

    public boolean isDiscountDisplayed() {
        return isElementVisible(getDiscountWebElement());
    }

    public boolean isRegularPriceDisplayed() {
        return isElementVisible(getRegularPriceWebElement());
    }

    public boolean isCorrectDiscount(String discountValue) {
        return parseDouble(discountValue) == (100 - (getPercentOfNumber(getRegularPrice(), getProductPrice())));
    }

    @Override
    public String toString() {
        return "Product info:\nName: " + getProductName() + "\nPrice: " + getProductPrice() + "\nRegular price:"
                + getRegularPrice() + "\nCurrency: " + getCurrency() + "\nImage: " + getProductImage()
                + "\nDiscount: " + getProductDiscount();
    }
}
