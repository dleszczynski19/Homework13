package com.shop.pages;

import com.shop.pages.models.items.OrderDetailsItemModel;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailsPage extends HeaderPage {
    private String[] deliveryAddress;
    private String[] invoiceAddress;
    private List<OrderDetailsItemModel> allOrderedItemList = new ArrayList<>();

    public OrderDetailsPage(WebDriver driver) {
        super(driver);
        waitForPageLoaded();
        setAddresses();
    }

    @FindBy(css = "#delivery-address address")
    public WebElement deliveryAddressInformation;

    @FindBy(css = "#invoice-address address")
    public WebElement invoiceAddressInformation;

    @FindBy(css = "#order-products tbody tr")
    private List<WebElement> orderedItemTable;

    public OrderDetailsPage setAddresses() {
        waitForPageLoaded();
        deliveryAddress = getElementText(deliveryAddressInformation).split("\\n");
        invoiceAddress = getElementText(invoiceAddressInformation).split("\\n");
        return this;
    }

    public String getFullName(boolean isDeliveryAddress) {
        String[] array;
        if (isDeliveryAddress) array = deliveryAddress;
        else array = invoiceAddress;
        return array[0];
    }

    public String getAddress(boolean isDeliveryAddress) {
        String[] array;
        if (isDeliveryAddress) array = deliveryAddress;
        else array = invoiceAddress;
        return array[1];
    }

    public String getZipCode(boolean isDeliveryAddress) {
        String[] array;
        if (isDeliveryAddress) array = deliveryAddress;
        else array = invoiceAddress;
        return array[2].replaceAll("[^0-9-]", "");
    }

    public String getCity(boolean isDeliveryAddress) {
        String[] array;
        if (isDeliveryAddress) array = deliveryAddress;
        else array = invoiceAddress;
        return array[2].replaceAll(getZipCode(isDeliveryAddress) + " ", "");
    }

    public String getCountry(boolean isDeliveryAddress) {
        String[] array;
        if (isDeliveryAddress) array = deliveryAddress;
        else array = invoiceAddress;
        return array[3];
    }

    public OrderDetailsPage setALlOrderedItemList() {
        for (WebElement item : orderedItemTable) {
            allOrderedItemList.add(new OrderDetailsItemModel(driver, item));
        }
        return this;
    }

    public List<OrderDetailsItemModel> getAllOrderedItemList() {
        return allOrderedItemList;
    }
}
