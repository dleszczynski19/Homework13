package com.shop.pages.models;

import com.shop.pages.HeaderPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

import java.util.List;

public class OrderHistoryModel extends HeaderPage {

    public OrderHistoryModel(WebDriver driver, WebElement element) {
        super(driver);
        PageFactory.initElements(new DefaultElementLocatorFactory(element), this);
    }

    @FindBy(css = "th")
    public WebElement orderReference;

    @FindBy(xpath = "td[1]")
    private WebElement date;

    @FindBy(xpath = "td[2]")
    private WebElement totalPrice;

    @FindBy(xpath = "td[3]")
    private WebElement payment;

    @FindBy(xpath = "td[4]")
    private WebElement status;

    @FindBy(xpath = "td[5]")
    private WebElement invoice;

    @FindBy(css = "td a")
    private List<WebElement> detailsAndReorder;

    @FindBy(css = "[data-link-action=\"view-order-details\"]")
    private WebElement orderDetails;

    public String getOrderReference() {
        return getElementText(orderReference);
    }

    public String getDate() {
        return getElementText(date);
    }

    public Double getTotalPrice() {
        return parseDouble(getElementText(totalPrice));
    }

    public String getPayment() {
        return getElementText(payment);
    }

    public String getStatus() {
        return getElementText(status);
    }

    public String getInvoice() {
        return getElementText(invoice);
    }

    public WebElement getReorder() {
        return findElementByName(detailsAndReorder, "Reorder");
    }

    public WebElement getDetails() {
        return orderDetails;
    }

    @Override
    public String toString() {
        return getOrderReference() + "\n" + getDate() + "\n" + getTotalPrice() + "\n" + getPayment() + "\n" + getStatus() + "\n" + getInvoice()
                + "\n" + getDetails() + "\n" + getReorder();
    }
}
