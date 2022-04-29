package com.shop.steps;

import com.shop.models.Shipping;
import com.shop.pages.*;
import com.shop.pages.configuration.userConfiguration.ShippingFactory;
import com.shop.pages.modals.AddToCartModalPage;
import com.shop.pages.models.ItemModel;
import com.shop.pages.models.OrderHistoryModel;
import com.shop.pages.models.items.OrderConfirmationRowModel;
import com.shop.pages.models.items.OrderDetailsItemModel;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.List;

public class CheckoutStep extends HeaderPage {
    private static AddToCartModalPage addPage;
    private ShippingFactory shippingFactory;
    private Shipping shipping;
    private CheckoutPage checkoutPage;
    private OrderConfirmationPage orderConfirmationPage;
    private HashMap<String, String> orderPropertyMap;
    private OrderHistoryModel order;
    private OrderDetailsPage orderDetailsPage;
    private List<ItemModel> basket;


    public CheckoutStep(WebDriver driver, Shipping shipping, List<ItemModel> basket) {
        super(driver);
        addPage = new AddToCartModalPage(driver);
        shippingFactory = new ShippingFactory(driver);
        checkoutPage = new CheckoutPage(driver);
        this.shipping = shipping;
        orderConfirmationPage = new OrderConfirmationPage(driver);
        this.basket = basket;
    }

    public CheckoutStep fillShoppingProperty() {
        addPage.proceedToCheckout();
        waitForPageLoaded();
        checkoutPage
                .fillAddress(shipping.getAddress())
                .fillCity(shipping.getCity())
                .fillZipCode(shipping.getZipCode())
                .selectState(shipping.getState())
                .selectCountry(shipping.getCountry())
                .saveDeliveryAddress()
                .chooseRandomShippingMethod()
                .confirmDelivery();
        return this;
    }

    public CheckoutStep fillPaymentMethod() {
        checkoutPage
                .choosePaymentMethod(CheckoutPage.PaymentBy.BANK_WIRE)
                .checkTermsOfUse()
                .closeModal()
                .agreeTermsOfUse()
                .placeOrder();
        return this;
    }

    public CheckoutStep checkOrderItems() {
        waitForPageLoaded();
        List<OrderConfirmationRowModel> allItemsList = orderConfirmationPage.setAllItemsList().getAllItemsList();
        for (ItemModel itemModel : basket) {
            OrderConfirmationRowModel order = allItemsList.stream()
                    .filter(orderRow -> orderRow.getProductUnitPrice() == itemModel.getProductPrice())
                    .filter(orderRow -> orderRow.getProductQuantity() == itemModel.getQuantityAmount())
                    .filter(orderRow -> orderRow.getProductTotalPrice() == itemModel.getQuantityAmount() * itemModel.getProductPrice())
                    .filter(orderRow -> orderRow.getProductName().contains(itemModel.getItemName()))
                    .reduce((f, s) -> f)
                    .orElseThrow(() -> new RuntimeException("Can't find item"));
            allItemsList.remove(order);
        }
        softAssert.assertThat(allItemsList.size()).isEqualTo(0);
        return this;
    }

    public CheckoutStep setOrder() {
        String orderReference = orderConfirmationPage.getOrderReference();
        goToMyAccount();
        new MyAccountPage(driver).goToOrderHistory();
        order = new OrderHistoryPage(driver)
                .setAllOrderList()
                .findOrderByReference(orderReference);
        orderPropertyMap = checkoutPage.getOrderPropertyMap();
        return this;
    }

    public CheckoutStep checkOrderProperties() {
        softAssert.assertThat(order.getTotalPrice().toString()).isEqualTo(orderPropertyMap.get("totalPrice"));
        softAssert.assertThat(order.getDate()).isEqualTo(orderPropertyMap.get("date"));
        softAssert.assertThat(order.getPayment()).contains(orderPropertyMap.get("paymentType"));
        softAssert.assertThat(order.getStatus()).isEqualTo(orderPropertyMap.get("paymentStatus"));
        return this;
    }

    public CheckoutStep checkOrderDetails() {
        clickOnElement(order.getDetails());
        orderDetailsPage = new OrderDetailsPage(driver);
        checkAddressesDetails(true);
        checkAddressesDetails(false);
        orderDetailsPage.setALlOrderedItemList();
        checkOrderedItems();
        return this;
    }

    public CheckoutStep checkOrderedItems() {
        List<OrderDetailsItemModel> allItemsList = orderDetailsPage.getAllOrderedItemList();

        for (ItemModel itemModel : basket) {
            OrderDetailsItemModel order = allItemsList.stream()
                    .filter(orderRow -> orderRow.getProductName().contains(itemModel.getItemName()))
                    .filter(orderRow -> orderRow.getProductTotalPrice() == itemModel.getQuantityAmount() * itemModel.getProductPrice())
                    .filter(orderRow -> orderRow.getProductUnitPrice() == itemModel.getProductPrice())
                    .filter(orderRow -> orderRow.getProductQuantity() == itemModel.getQuantityAmount())
                    .reduce((f, s) -> f)
                    .orElseThrow(() -> new RuntimeException("Can't find option"));
            allItemsList.remove(order);
        }
        softAssert.assertThat(allItemsList.size()).isEqualTo(0);
        return this;
    }

    private CheckoutStep checkAddressesDetails(boolean isDeliveryAddress) {
        softAssert.assertThat(orderDetailsPage.getAddress(isDeliveryAddress)).isEqualTo(orderPropertyMap.get("address"));
        softAssert.assertThat(orderDetailsPage.getZipCode(isDeliveryAddress)).isEqualTo(orderPropertyMap.get("code"));
        softAssert.assertThat(orderDetailsPage.getCity(isDeliveryAddress)).isEqualTo(orderPropertyMap.get("city"));
        softAssert.assertThat(orderDetailsPage.getCountry(isDeliveryAddress)).isEqualTo(orderPropertyMap.get("country"));
        return this;
    }

    public CheckoutStep assertCheckoutTest() {
        softAssert.assertAll();
        return this;
    }
}
