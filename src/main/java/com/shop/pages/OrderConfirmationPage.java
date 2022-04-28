package com.shop.pages;

import com.shop.pages.models.OrderRowModel;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class OrderConfirmationPage extends HeaderPage {
    private List<OrderRowModel> allItemsList;

    public OrderConfirmationPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = ".material-icons.rtl-no-flip.done")
    private WebElement doneIcon;

    @FindBy(css = ".order-line.row")
    private List<WebElement> orderedItemsList;

    @FindBy(css = "#order-details li")
    private List<WebElement> orderDetails;

    public OrderConfirmationPage setAllItemsList() {
        waitForElement(doneIcon);
        allItemsList = new ArrayList<>();
        waitForListSizeIsHigherThanZero(orderedItemsList);
        for (WebElement item : orderedItemsList) {
            allItemsList.add(new OrderRowModel(driver, item));
        }
        return this;
    }

    public String getOrderReference() {
        return getElementText(findElementByContainName(orderDetails, "Order reference:"))
                .replaceAll("Order reference: ", "");
    }

    public List<OrderRowModel> getAllItemsList() {
        return allItemsList;
    }
}
