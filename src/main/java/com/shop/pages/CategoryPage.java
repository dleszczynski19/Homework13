package com.shop.pages;

import com.shop.pages.models.ProductModel;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class CategoryPage extends HeaderPage {
    private Logger log = LoggerFactory.getLogger(CategoryPage.class);
    private String pagePath = "Home ";
    private WebElement category;
    private String categoryName;
    private List<ProductModel> allProductsList;

    public CategoryPage(WebDriver driver, WebElement category, String categoryName) {
        super(driver);
        this.category = category;
        this.categoryName = categoryName;
    }

    @FindBy(css = "#js-product-list-header h1")
    private WebElement categoryHeader;

    @FindBy(css = "#js-product-list-top")
    private WebElement rowSelections;

    @FindBy(css = ".product")
    private List<WebElement> productsList;

    @FindBy(css = "#search_filters li p")
    private WebElement priceFilterLabel;

    @FindBy(css = ".ui-slider-handle.ui-state-default.ui-corner-all")
    private List<WebElement> priceSliderList;

    public boolean isOnCategoryPage(String categoryParent) {
        if (categoryParent != null) {
            return isCorrectPageLoaded(pagePath + getEachFirstWordLetterUpperCase(categoryParent) + " "
                    + getEachFirstWordLetterUpperCase(categoryName));
        } else return isCorrectPageLoaded(pagePath + getOnlyFirstLetterUpperCase(categoryName));
    }

    public boolean isCategoryNameEqualsHeader(String categoryName) {
        String categoryHeader = getCategoryHeader();
        if (categoryName.equals(categoryHeader)) {
            log.info("Category name " + categoryName + " is equal to header");
            return true;
        } else {
            log.error("Category name " + categoryName + " is not equal  to header: " + categoryHeader);
            return false;
        }
    }

    public CategoryPage setALlCategoryProducts() {
        waitForPageLoaded();
        waitForListSizeIsHigherThanZero(productsList);
        allProductsList = new ArrayList<>();
        for (WebElement product : productsList) {
            allProductsList.add(new ProductModel(driver, product));
        }
        log.info("All products on page set");
        return this;
    }

    public boolean isProductsPriceMatchWithFilter() {
        boolean isTrue = true;
        for (ProductModel productModel : allProductsList) {
            if (isTrue) {
                double productPrice = productModel.getProductPrice();
                isTrue = productPrice >= getPriceFilter(true) &&
                        productPrice <= getPriceFilter(false);
                log.info("Product price: " + productPrice);
            } else return false;
        }
        return isTrue;
    }

    public boolean isLabelAmountEqualsProductAmount() {
        int productAmount = getProductsAmount();
        int labelAmount = getLabelProductsAmount();
        if (productAmount == labelAmount) {
            log.info("Label products amount is equal to products amount : " + productAmount);
            return true;
        } else {
            log.info("Label products amount: " + labelAmount + " is not equal to products amount: " + productAmount);
            return false;
        }
    }

    public String[] getPriceFilterValues() {
        return priceFilterLabel.getText().replaceAll("[^0-9.-]", "").split("-");
    }

    public double getPriceFilter(boolean isMin) {
        if (isMin) return parseDouble(getPriceFilterValues()[0]);
        else return parseDouble(getPriceFilterValues()[1]);
    }

    public CategoryPage setPriceFilter(double filterValue, boolean isMin) {
        WebElement slider;
        if (isMin) slider = priceSliderList.get(0);
        else slider = priceSliderList.get(1);
        Keys direction;
        double priceFilter = getPriceFilter(isMin);
        double slideValue = filterValue - priceFilter;
        if (priceFilter != filterValue) {
            if (slideValue > 0) direction = Keys.ARROW_RIGHT;
            else direction = Keys.ARROW_LEFT;
            for (int i = 0; i < Math.abs(slideValue); i++) {
                sendActionToElement(slider, direction);
            }
        }
        log.info("Value of isMin: " + isMin + " set to: " + filterValue);
        return this;
    }

    public String getCategoryHeader() {
        return categoryHeader.getText();
    }

    public int getProductsAmount() {
        return productsList.size();
    }

    public int getLabelProductsAmount() {
        return parseInt(rowSelections.getText());
    }

    public List<ProductModel> getAllProductsList() {
        return allProductsList;
    }
}
