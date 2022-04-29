package com.shop.steps;

import com.shop.pages.*;
import com.shop.pages.models.ItemModel;
import com.shop.pages.models.items.BasketRowModel;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class HeaderStep extends HeaderPage {
    private CategoryPage categoryPage;
    private Logger log = LoggerFactory.getLogger(HeaderStep.class);
    private ProductPage productPage;
    private HomePage homePage;
    private CartPage cartPage;

    public HeaderStep(WebDriver driver, HomePage homePage) {
        super(driver);
        productPage = new ProductPage(driver);
        this.homePage = homePage;
        cartPage = new CartPage(driver);
    }

    public HeaderStep checkEachCategoryProperties() {
        setCategoriesList();
        for (WebElement category : getCategoriesList()) {
            hoverOnCategory(category);
            checkSubMenuDisplayed(category);
            checkElementValues(category);
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
            checkElementValues(subcategoriesList.get(i), category);
            goToHomePage();
        }
        return this;
    }

    private HeaderStep checkElementValues(WebElement category, WebElement... parentCategory) {
        WebElement link = findElementInAnotherElement(category, "a");
        String elementName = link.getText();
        String parentCategoryName = null;
        categoryPage = new CategoryPage(driver, category, elementName);
        if (parentCategory.length == 1) {
            parentCategoryName = findElementInAnotherElement(parentCategory[0], "a").getText();
        }
        clickOnElement(link);
        softAssert.assertThat(categoryPage.isOnCategoryPage(parentCategoryName)).isTrue();
        softAssert.assertThat(categoryPage.isCategoryNameEqualsHeader(elementName)).isTrue();
        softAssert.assertThat(categoryPage.isLabelAmountEqualsProductAmount()).isTrue();
        log.info("Element: " + elementName + " checked");
        return this;
    }

    public HeaderStep clickOnRandomItem() {
        setCategoriesList();
        WebElement category = getRandomElement(getCategoriesList());
        String categoryName = category.getText();
        clickOnElement(category);
        categoryPage = new CategoryPage(driver, category, categoryName);
        new CategoryStep(driver, categoryPage, homePage).goToRandomItem();
        return this;
    }

    public boolean isItemProperlyAddToCart() {
        return new ProductSteps(driver, productPage, homePage).isItemProperlyAdded();
    }

    public HeaderStep addItemsToBasket(int itemCount, boolean isContinueShopping) {
        for (int i = 0; i < itemCount; i++) {
            addItemToBasket(isContinueShopping);
            goToHomePage();
        }
        log.info("Items properly add to basket");
        return this;
    }

    public HeaderStep addItemsToBasket(int itemCount, int quantityCount, boolean isContinueShopping) {
        for (int i = 0; i < itemCount; i++) {
            addItemToBasket(quantityCount, isContinueShopping);
            goToHomePage();
        }
        log.info("Items properly add to basket");
        return this;
    }

    public HeaderStep addItemToBasket(boolean isContinueShopping) {
        clickOnRandomItem();
        productPage
                .setCustomizableMessage()
                .createItemModel()
                .addItemToCart()
                .chooseShoppingAction(isContinueShopping);
        return this;
    }

    public HeaderStep addItemToBasket(int quantity, boolean isContinueShopping) {
        clickOnRandomItem();
        productPage
                .setCustomizableMessage()
                .setQuantityValue(quantity)
                .createItemModel()
                .addItemToCart()
                .chooseShoppingAction(isContinueShopping);
        return this;
    }

    public HeaderStep checkBasketItems() {
        List<ItemModel> itemList = getItemList();
        List<BasketRowModel> allItemsList = cartPage.setAllItemsList().getAllItemsList();
        for (BasketRowModel basketRowModel : allItemsList) {
            List<ItemModel> collect = itemList.stream()
                    .filter(itemModel -> itemModel.getItemName().equals(basketRowModel.getProductName()))
                    .filter(itemModel -> itemModel.getProductPrice() == basketRowModel.getProductUnitPrice())
                    .filter(itemModel -> itemModel.getQuantityAmount() == basketRowModel.getProductQuantity())
                    .collect(Collectors.toList());
            itemList.remove(collect.get(0));
        }
        softAssert.assertThat(itemList.size()).isEqualTo(0);
        return this;
    }

    public List<ItemModel> getItemList() {
        return productPage.getItemCartModel().getItemsList();
    }

    public HeaderStep checkBasketQuantity() {
        cartPage.setAllItemsList()
                .isQuantityAdd(parseInt(System.getProperty("basketTestProductQuantity")))
                .isQuantityAdd(parseInt(System.getProperty("basketTestProductQuantity")))
                .isQuantityAdd(parseInt(System.getProperty("basketTestProductQuantity")));
        return this;
    }

    public HeaderStep checkEachItemDeleted() {
        List<BasketRowModel> allItemsList = cartPage.setAllItemsList()
                .getAllItemsList();
        for (int i = 0; i < allItemsList.size(); i++) {
            cartPage.deleteItemFromBasket(cartPage.getRandomIndexFromItemList());
        }
        return this;
    }

    public HeaderStep assertBasketTest() {
        cartPage.assertAll();
        softAssert.assertAll();
        return this;
    }

    public HeaderStep assertCategoriesTest() {
        assertAll();
        softAssert.assertAll();
        return this;
    }
}