package com.shop.pages.modals;

import com.shop.pages.HeaderPage;
import com.shop.pages.models.ItemModel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddToCartModalPage extends HeaderPage {
    private static Logger log = LoggerFactory.getLogger(AddToCartModalPage.class);
    private ItemModel itemModel;
    private int quantityCount = 0;

    public AddToCartModalPage(WebDriver driver) {
        super(driver);
    }

    public AddToCartModalPage(WebDriver driver, ItemModel itemModel) {
        super(driver);
        WebElement element = driver.findElement(By.cssSelector("#blockcart-modal"));
        PageFactory.initElements(new DefaultElementLocatorFactory(element), this);
        this.itemModel = itemModel;
        log.info("Modal for: " + itemModel.getItemName());
    }

    public AddToCartModalPage(WebDriver driver, ItemModel itemModel, int quantityCount) {
        super(driver);
        WebElement element = driver.findElement(By.cssSelector("#blockcart-modal"));
        PageFactory.initElements(new DefaultElementLocatorFactory(element), this);
        this.itemModel = itemModel;
        this.quantityCount = quantityCount;
        log.info("Modal for: " + itemModel.getItemName());
    }

    @FindBy(css = ".cart-content-btn button")
    private WebElement continueShoppingButton;

    @FindBy(css = ".cart-content .cart-products-count")
    private WebElement productsCountLabel;

    @FindBy(css = ".h6.product-name")
    private WebElement modalProductName;

    @FindBy(css = ".product-price")
    private WebElement modalProductPrice;

    @FindBy(css = ".product-quantity")
    private WebElement modalProductQuantity;

    @FindBy(css = ".modal-header button")
    private WebElement closeModalWindow;

    @FindBy(css = "a.btn.btn-primary")
    private WebElement checkoutButton;

    public String getProductName() {
        String name = getElementText(modalProductName);
        log.info("Products modal name: " + name);
        return name;
    }

    public int getProductQuantity() {
        int count = parseInt(getElementText(modalProductQuantity));
        log.info("Products modal quantity: " + count);
        return count;
    }

    public double getProductPrice() {
        double price = parseDouble(getElementText(modalProductPrice));
        log.info("Products modal price: " + price);
        return price;
    }

    public int getProductsCount() {
        int count = parseInt(getElementText(productsCountLabel));
        log.info("Products count: " + count);
        return count;
    }

    public AddToCartModalPage chooseShoppingAction(boolean isContinueShopping) {
        if (isContinueShopping) continueShopping();
        else proceedToCheckout();
        return this;
    }

    public AddToCartModalPage continueShopping() {
        clickOnElement(continueShoppingButton);
        return this;
    }

    public AddToCartModalPage proceedToCheckout() {
        clickOnElement(checkoutButton);
        return this;
    }

    public AddToCartModalPage closeModalWindow() {
        clickOnElement(closeModalWindow);
        return this;
    }

    public boolean isProperlyName() {
        boolean isTrue = getProductName().equals(itemModel.getItemName());
        log.info("Is properly name item add to cart: " + isTrue);
        return isTrue;
    }

    public boolean isProperlyPrice() {
        boolean isTrue = getProductPrice() == itemModel.getProductPrice();
        log.info("Is properly price item add to cart: " + isTrue);
        return isTrue;
    }

    public boolean isProperlyQuantityCount() {
        boolean isTrue = getProductQuantity() == itemModel.getQuantityAmount();
        log.info("Item quantity: " + itemModel.getQuantityAmount());
        log.info("Is properly count of item add to cart: " + isTrue);
        return isTrue;
    }

    public boolean isProperlyLabel() {
        int count = getProductsCount();
        boolean isTrue = count == quantityCount - (quantityCount - count);
        log.info("Is properly label displayed: " + isTrue);
        return isTrue;
    }

    public boolean isProperlyItemAdd() {
        boolean isTrue = isProperlyLabel() && isProperlyName() && isProperlyPrice() && isProperlyQuantityCount();
        continueShopping();
        return isTrue;
    }
}