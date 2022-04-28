package com.shop.pages.models;

import com.shop.pages.HeaderPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

public class OrderRowModel extends HeaderPage {

    public OrderRowModel(WebDriver driver, WebElement orderRow) {
        super(driver);
        PageFactory.initElements(new DefaultElementLocatorFactory(orderRow), this);
    }

    @FindBy(css = ".col-sm-4.col-xs-9.details")
    private WebElement productInformation;

    @FindBy(css = ".col-xs-4.text-sm-center.text-xs-left")
    private WebElement productUnitPrice;

    @FindBy(css = ".col-xs-4.text-sm-center:nth-child(2)")
    private WebElement productQuantity;

    @FindBy(css = ".col-xs-4.text-sm-center.text-xs-right.bold")
    private WebElement productTotalPrice;

    public String getProductInformation() {
        return getElementText(productInformation);
    }

    public double getProductUnitPrice() {
        return parseDouble(getElementText(productUnitPrice));
    }

    public double getProductTotalPrice() {
        return parseDouble(getElementText(productTotalPrice));
    }

    public int getProductQuantity() {
        return parseInt(getElementText(productQuantity));
    }

    @Override
    public String toString() {
        return getProductInformation() + "\n" + getProductUnitPrice() + "\n" + getProductTotalPrice() + "\n" + getProductQuantity();
    }
}
