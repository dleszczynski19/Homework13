package com.shop.steps;

import com.shop.pages.CategoryPage;
import com.shop.pages.HeaderPage;
import com.shop.pages.HomePage;
import com.shop.pages.ProductPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class HeaderStep extends HeaderPage {
    private CategoryPage categoryPage;
    private Logger log = LoggerFactory.getLogger(HeaderStep.class);
    private ProductPage productPage;
    private HomePage homePage;

    public HeaderStep(WebDriver driver, HomePage homePage) {
        super(driver);
        productPage = new ProductPage(driver);
        this.homePage = homePage;
    }

    public HeaderStep checkEachCategoryProperties() {
        setCategoriesList();
        for (WebElement category : getCategoriesList()) {
            hoverOnCategory(category);
            assert isSubMenuDisplayed(category) && checkElementValues(category);
            goToHomePage();
        }
        return this;
    }

    public HeaderStep checkEachSubcategoryProperties() {
        for (WebElement category : getCategoriesList()) {
            checkSubcategory(category);
        }
        return this;
    }

    public HeaderStep checkSubcategory(WebElement category) {
        List<WebElement> subcategoriesList = getSubCategoriesList(category);

        for (int i = 0; i < subcategoriesList.size(); i++) {
            subcategoriesList = getSubCategoriesList(category);
            assert checkElementValues(subcategoriesList.get(i), category) : "Wrong value for subcategory";
            goToHomePage();
        }
        return this;
    }

    private boolean checkElementValues(WebElement category, WebElement... parentCategory) {
        WebElement link = findElementInAnotherElement(category, "a");
        boolean assertion;
        String elementName = link.getText();
        String parentCategoryName = null;
        categoryPage = new CategoryPage(driver, category, elementName);
        if (parentCategory.length == 1) {
            parentCategoryName = findElementInAnotherElement(parentCategory[0], "a").getText();
        }
        clickOnElement(link);
        assertion = categoryPage.isOnCategoryPage(parentCategoryName) &&
                categoryPage.isCategoryNameEqualsHeader(elementName) &&
                categoryPage.isLabelAmountEqualsProductAmount();
        log.info("Element: " + elementName + " checked status: " + assertion);
        return assertion;
    }

    public HeaderStep addRandomItemToCart() {
        setCategoriesList();
        WebElement category = getRandomElement(getCategoriesList());
        String categoryName = category.getText();
        clickOnElement(category);
        categoryPage = new CategoryPage(driver, category, categoryName);
        new CategoryStep(driver, categoryPage).goToRandomItem();
        assert new ProductSteps(driver, productPage, homePage).isItemProperlyAdded() : "Item is not properly add to shopping cart";
        goToHomePage();
        return this;
    }
}
