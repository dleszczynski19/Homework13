package com.shop.pages;

import com.shop.pages.models.items.BasketRowModel;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class CartPage extends HeaderPage {
    private List<BasketRowModel> allItemsList;
    private int itemCountBefore;
    private double totalPriceBefore;
    private double shippingPrice;

    public CartPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = ".product-line-grid")
    private List<WebElement> basketRow;

    @FindBy(css = ".label.js-subtotal")
    private WebElement itemsInBasketCount;

    @FindBy(css = "#cart-subtotal-products .value")
    private WebElement productsCost;

    @FindBy(css = "#cart-subtotal-shipping span.value")
    private WebElement shippingCost;

    @FindBy(css = ".cart-summary-line.cart-total .value")
    private WebElement totalCost;

    @FindBy(css = ".checkout.cart-detailed-actions.card-block a")
    private WebElement checkoutButton;

    public CartPage setAllItemsList() {
        allItemsList = new ArrayList<>();
        for (WebElement item : basketRow) {
            allItemsList.add(new BasketRowModel(driver, item));
        }
        return this;
    }

    public int getRandomIndexFromItemList() {
        setAllItemsList();
        return getRandomNumber(0, allItemsList.size() - 1);
    }

    public CartPage deleteItemFromBasket(int itemIndex) {
        totalPriceBefore = getTotalCost();
        itemCountBefore = getItemsInBasketCount();
        shippingPrice = getShippingCost();
        BasketRowModel item = allItemsList.get(itemIndex);
        double itemPrice = item.getProductTotalPrice();
        int itemQuantity = item.getProductQuantity();
        clickOnElement(item.getDeleteProductIcon());
        waitForPageLoaded();
        softAssert.assertThat(itemCountBefore).isEqualTo(getItemsInBasketCount() + itemQuantity);
        if (allItemsList.size() == 1) {
            softAssert.assertThat(roundUpToSecondDecimalPlace(totalPriceBefore - itemPrice))
                    .isEqualTo(getTotalCost() + shippingPrice);
        } else {
            softAssert.assertThat(roundUpToSecondDecimalPlace(totalPriceBefore - itemPrice))
                    .isEqualTo(getTotalCost());
        }
        return this;
    }

    public CartPage isQuantityAdd(int quantityToAdd) {
        BasketRowModel basketRowModel = allItemsList.get(parseInt(System.getProperty("basketTestProductToModify")));
        int quantityBefore = basketRowModel.getProductQuantity();
        double priceBefore = basketRowModel.getProductTotalPrice();
        double priceForOneItem = priceBefore / quantityBefore;
        basketRowModel.setProductQuantity(quantityToAdd);
        setAllItemsList();
        basketRowModel = allItemsList.get(parseInt(System.getProperty("basketTestProductToModify")));
        softAssert.assertThat(basketRowModel.getProductQuantity()).isEqualTo(quantityToAdd);
        softAssert.assertThat(roundUpToSecondDecimalPlace(priceForOneItem * quantityToAdd)).isEqualTo(basketRowModel.getProductTotalPrice());
        return this;
    }

    public CartPage assertAll() {
        softAssert.assertAll();
        return this;
    }

    public CartPage proceedToCheckout() {
        clickOnElement(checkoutButton);
        return this;
    }

    public int getItemsInBasketCount() {
        return parseInt(getElementText(itemsInBasketCount));
    }

    public double getProductsCost() {
        return parseDouble(getElementText(productsCost));
    }

    public double getShippingCost() {
        String shipping = getElementText(shippingCost);
        if (shipping.equals("Free")) return 0;
        return parseDouble(shipping);
    }

    public double getTotalCost() {
        return parseDouble(getElementText(totalCost));
    }

    public List<BasketRowModel> getAllItemsList() {
        return allItemsList;
    }
}