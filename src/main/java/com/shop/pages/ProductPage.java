package com.shop.pages;

import com.shop.pages.configuration.WebElementHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductPage extends WebElementHelper {
    private Logger log = LoggerFactory.getLogger(ProductPage.class);
    private WebElement product;

    public ProductPage(WebDriver driver, WebElement product) {
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

    public String getProductName() {
        return productName.getText();
    }

    public double getProductPrice() {
        return Double.parseDouble(productPrice.getText().replaceAll("[^0-9.]", ""));
    }

    public Object getRegularPrice() {
        try {
            return Double.parseDouble(productPrice.getText().replaceAll("[^0-9.]", ""));
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

    public WebElement getProduct() {
        return product;
    }

    @Override
    public String toString() {
        return "Product info:\nName: " + getProductName() + "\nPrice: " + getProductPrice() + "\nRegular price:"
                + getRegularPrice() + "\nCurrency: " + getCurrency() + "\nImage: " + getProductImage();
    }
}
