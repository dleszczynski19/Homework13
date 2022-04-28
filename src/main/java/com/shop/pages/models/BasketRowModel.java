package com.shop.pages.models;

import com.shop.pages.HeaderPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasketRowModel extends HeaderPage {
    private Logger log = LoggerFactory.getLogger(BasketRowModel.class);

    public BasketRowModel(WebDriver driver, WebElement basketRow) {
        super(driver);
        PageFactory.initElements(new DefaultElementLocatorFactory(basketRow), this);
    }

    @FindBy(css = "img")
    private WebElement productImage;

    @FindBy(css = ".product-line-info a")
    private WebElement productName;

    @FindBy(css = ".current-price")
    private WebElement productPrice;

    @FindBy(css = ".product-line-info.size .value")
    private WebElement productSize;

    @FindBy(css = ".product-line-info.color .value")
    private WebElement productColor;

    @FindBy(css = ".product-line-info.dimension .value")
    private WebElement productDimension;

    @FindBy(css = ".input-group.bootstrap-touchspin input")
    private WebElement productQuantity;

    @FindBy(css = ".row .product-price strong")
    private WebElement productSummaryPrice;

    @FindBy(css = ".product-line-grid .cart-line-product-actions i")
    private WebElement deleteProductIcon;

    @FindBy(css = ".material-icons.touchspin-up")
    private WebElement arrowUpQuantity;

    @FindBy(css = ".material-icons.touchspin-down")
    private WebElement arrowDownQuantity;

    public String getProductImage() {
        return getElementText(productImage);
    }

    public String getProductName() {
        return getElementText(productName);
    }

    public double getProductPrice() {
        return parseDouble(getElementText(productPrice));
    }

    public String getProductSize() {
        try {
            return getElementText(productSize);
        } catch (Exception e) {
            log.warn("Product don't have size");
            return null;
        }
    }

    public String getProductColor() {
        try {
            return getElementText(productColor);
        } catch (Exception e) {
            log.warn("Product don't have color");
            return null;
        }
    }

    public String getProductDimension() {
        try {
            return getElementText(productDimension);
        } catch (Exception e) {
            log.warn("Product don't have dimension");
            return null;
        }
    }

    public int getProductQuantity() {
        return parseInt(getElementValue(productQuantity));
    }

    public Double getProductSummaryPrice() {
        return parseDouble(getElementText(productSummaryPrice));
    }

    public WebElement getProductQuantityInput() {
        return productQuantity;
    }

    public WebElement getDeleteProductIcon() {
        return deleteProductIcon;
    }

    public WebElement getArrowUpQuantity() {
        return arrowUpQuantity;
    }

    public WebElement getArrowDownQuantity() {
        return arrowDownQuantity;
    }

    public void setProductQuantity(int productCount) {
        WebElement element;
        int currentValue = getProductQuantity();
        int addValue = productCount - currentValue;
        if (currentValue != productCount) {
            if (addValue > 0) element = getArrowUpQuantity();
            else element = getArrowDownQuantity();
            for (int i = 0; i < Math.abs(addValue); i++) {
                clickOnElement(element);
            }
        }
        waitForPageLoaded();
    }

    @Override
    public String toString() {
        return "Name: " + getProductName() + "\nPrice: " + getProductPrice() + "\nSize: " + getProductSize() + "\nColor: " + getProductColor()
                + "\nDimension: " + getProductDimension() + "\nQuantity: " + getProductQuantity() + "\nSummary: " + getProductSummaryPrice()
                + "\n------------------------------------------------------";
    }
}
