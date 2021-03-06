package com.shop.pages.models.items;

import com.shop.pages.HeaderPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

public class OrderDetailsItemModel extends HeaderPage implements productsProperties {

    public OrderDetailsItemModel(WebDriver driver, WebElement element) {
        super(driver);
        PageFactory.initElements(new DefaultElementLocatorFactory(element), this);
    }

    @FindBy(xpath = "td[1]")
    private WebElement productInformation;

    @FindBy(xpath = "td[2]")
    private WebElement productQuantity;

    @FindBy(xpath = "td[3]")
    private WebElement productUnitPrice;

    @FindBy(xpath = "td[4]")
    private WebElement productTotalPrice;

    public String getProductName() {
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
        return getProductName() + "\n" + getProductUnitPrice() + "\n" + getProductTotalPrice() + "\n" + getProductQuantity();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        OrderDetailsItemModel order = (OrderDetailsItemModel) object;
        return getProductName().equals(order.getProductName()) && getProductUnitPrice() == (order.getProductUnitPrice())
                && getProductTotalPrice() == (order.getProductTotalPrice()) && getProductQuantity() == order.getProductQuantity();
    }
}
