package com.shop.pages;

import com.shop.pages.models.OrderHistoryModel;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryPage extends HeaderPage {
    private List<OrderHistoryModel> allOrderList = new ArrayList<>();

    @FindBy(css = "tbody tr")
    private List<WebElement> orderTable;

    public OrderHistoryPage(WebDriver driver) {
        super(driver);
    }

    public OrderHistoryPage setAllOrderList() {
        for (WebElement order : orderTable) {
            allOrderList.add(new OrderHistoryModel(driver, order));
        }
        return this;
    }

    public List<OrderHistoryModel> getAllOrderList() {
        return allOrderList;
    }

    public OrderHistoryModel findOrderByReference(String reference) {
        return allOrderList.stream().filter(o -> o.getOrderReference().equals(reference))
                .reduce((f, s) -> s)
                .orElseThrow(() -> new RuntimeException("Can't find order"));
    }
}
