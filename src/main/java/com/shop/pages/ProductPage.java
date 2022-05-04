package com.shop.pages;

import com.shop.pages.modals.AddToCartModalPage;
import com.shop.pages.models.ItemCartModel;
import com.shop.pages.models.ItemModel;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class ProductPage extends HeaderPage {
    private Logger log = LoggerFactory.getLogger(ProductPage.class);
    private String productName;
    private ItemCartModel itemCartModel;
    private ItemModel itemModel;
    private int quantityCount = 0;

    public ProductPage(WebDriver driver) {
        super(driver);
        itemCartModel = new ItemCartModel(driver);
    }

    public ProductPage(WebDriver driver, String productName) {
        super(driver);
        this.productName = productName;
        itemCartModel = new ItemCartModel(driver);
    }

    @FindBy(css = "h1[itemprop=\"name\"]")
    private WebElement itemName;

    @FindBy(css = ".discount.discount-percentage")
    private WebElement discountLabel;

    @FindBy(css = ".regular-price")
    private WebElement productRegularPrice;

    @FindBy(css = ".current-price [itemprop=\"price\"]")
    private WebElement productPrice;

    @FindBy(css = "#quantity_wanted")
    private WebElement quantityAmount;

    @FindBy(css = ".product-information [itemprop=\"description\"]")
    private WebElement productDescription;

    @FindBy(css = "#group_1")
    private WebElement productSize;

    @FindBy(css = "#group_3")
    private WebElement productDimension;

    @FindBy(css = "#group_2 [checked=\"checked\"]")
    private WebElement productColour;

    @FindBy(css = ".add button")
    private WebElement buttonAddToCart;

    @FindBy(css = "#myModalLabel")
    private WebElement successfullyAddLabel;

    @FindBy(css = ".clearfix .btn-primary.float-xs-right")
    private WebElement customization;

    @FindBy(css = ".clearfix .product-message")
    private WebElement customizationMessage;

    public boolean isDiscountLabelDisplayed() {
        return isElementVisible(discountLabel);
    }

    public ProductPage createItemModel() {
        waitForPageLoaded();
        itemModel = new ItemModel(
                getProductName(),
                getDiscountLabel(),
                getProductRegularPrice(),
                getProductPrice(),
                getQuantityAmount(),
                getProductDescription(),
                getProductSize(),
                getProductColor(),
                getDimension(),
                isCustomizable());
        return this;
    }

    public AddToCartModalPage addItemToCart() {
        quantityCount = getQuantityAmount();
        clickOnElement(buttonAddToCart);
        waitForElement(successfullyAddLabel);
        if (isElementVisible(successfullyAddLabel)) {
            if (itemCartModel.getItemsList().stream().anyMatch(item -> item.getItemName().equals(getProductName()))) {
                itemCartModel.getItemsList().stream()
                        .filter(item -> item.getItemName().equals(getProductName()))
                        .forEach(item -> item.setQuantityAmount(item.getQuantityAmount() + quantityCount));
                List<ItemModel> collect = itemCartModel.getItemsList().stream().filter(item -> item.getItemName().equals(getProductName()))
                        .collect(Collectors.toList());
                itemModel = collect.get(0);
            } else itemCartModel.addItem(itemModel);
            log.info("Item added");
        } else log.warn("Can't add item to cart");
        return new AddToCartModalPage(driver, itemModel, quantityCount);
    }

    public List<ItemModel> getShoppingCart() {
        return itemCartModel.getItemsList();
    }

    public boolean isCorrectLabelDisplayed(String label) {
        waitForElement(discountLabel);
        return label.equals(getElementText(discountLabel));
    }

    public String getDiscountLabel() {
        try {
            return getElementText(discountLabel);
        } catch (Exception e) {
            log.warn("Item don't have discount");
            return null;
        }
    }

    public boolean isPriceDisplayed() {
        return isElementVisible(productPrice);
    }

    public boolean isRegularPriceDisplayed() {
        return isElementVisible(productRegularPrice);
    }

    public String getProductName() {
        String productName = getElementText(itemName);
        this.productName = productName;
        return productName;
    }

    public Double getProductPrice() {
        if (isPriceDisplayed()) {
            double price = parseDouble(getElementText(productPrice));
            log.info(productName + " price: " + price);
            return price;
        } else return null;
    }

    private boolean isCustomizable() {
        boolean isCustomizable = isElementVisible(customization);
        log.info(productName + " isCustomizable: " + isCustomizable);
        return isCustomizable;
    }

    public Double getProductRegularPrice() {
        if (isRegularPriceDisplayed()) {
            double regularPrice = parseDouble(getElementText(productRegularPrice));
            log.info(productName + " regular price: " + regularPrice);
            return regularPrice;
        } else return null;
    }

    public int getQuantityAmount() {
        int amount = parseInt(getElementValue(quantityAmount));
        log.info(productName + " amount: " + amount);
        return amount;
    }

    public String getProductDescription() {
        String description = getElementText(productDescription);
        log.info(productName + " description: " + description);
        return description;
    }

    public String getProductSize() {
        try {
            String size = getElementText(getSelect(productSize).getFirstSelectedOption());
            log.info(productName + " size: " + size);
            return size;
        } catch (Exception e) {
            log.warn("Product don't have size to choose");
            return null;
        }
    }

    public String getDimension() {
        try {
            String dimension = getElementText(getSelect(productDimension).getFirstSelectedOption());
            log.info(productName + " size: " + dimension);
            return dimension;
        } catch (Exception e) {
            log.warn("Product don't have dimension to choose");
            return null;
        }
    }

    public String getProductColor() {
        try {
            String color = getElementAttribute(productColour, "title");
            log.info(productName + " color: " + color);
            return color;
        } catch (Exception e) {
            log.warn("Product don't have color to choose");
            return null;
        }
    }

    public ProductPage setQuantityValue(int quantityCount) {
        sendKeysToElement(quantityAmount, String.valueOf(quantityCount));
        return this;
    }

    public ProductPage setCustomizableMessage() {
        if (isCustomizable()) {
            sendKeysToElement(customizationMessage, getRandomFirstName());
            clickOnElement(customization);
        }
        waitForPageLoaded();
        return this;
    }

    public boolean isCorrectDiscount(String discountValue) {
        return parseDouble(discountValue) == (100 - (getPercentOfNumber(getProductRegularPrice(), getProductPrice())));
    }

    public ItemCartModel getItemCartModel() {
        return itemCartModel;
    }
}