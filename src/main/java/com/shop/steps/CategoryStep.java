package com.shop.steps;

import com.shop.pages.CategoryPage;
import com.shop.pages.HeaderPage;
import com.shop.pages.HomePage;
import com.shop.pages.models.ProductModel;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;

public class CategoryStep extends HeaderPage {
    private CategoryPage categoryPage;
    private HomePage homePage;

    public CategoryStep(WebDriver driver, HomePage homePage) {
        super(driver);
        this.homePage = homePage;
    }

    public CategoryStep(WebDriver driver, CategoryPage categoryPage, HomePage homePage) {
        super(driver);
        this.categoryPage = categoryPage;
        this.homePage = homePage;
    }

    public CategoryStep goToRandomItem() {
        categoryPage.isOnCategoryPage(null);
        categoryPage.setALlCategoryProducts();
        clickOnElement(categoryPage.getAllProductsList()
                .get(getRandomNumber(0, categoryPage.getAllProductsList().size() - 1))
                .getProductLink());
        return this;
    }

    public double[] setPricesArray() {
        List<ProductModel> allProductsList = categoryPage
                .setALlCategoryProducts()
                .getAllProductsList();
        double[] array = new double[allProductsList.size()];
        for (int i = 0; i < allProductsList.size(); i++) {
            array[i] = allProductsList.get(i).getProductPrice();
        }
        return Arrays.stream(array).distinct().sorted().toArray();
    }

    public CategoryStep goToCategory(WebElement category) {
        String categoryName = category.getText();

        homePage.goToCategory(category);
        categoryPage = new CategoryPage(driver, category, categoryName);
        softAssert.assertThat(categoryPage.isOnCategoryPage(null)).isTrue();
        return this;
    }

    public CategoryStep checkIsPriceMatchWithFilter(double minFilterValue, double maxFilterValue) {
        categoryPage.setPriceFilter(minFilterValue, true);
        categoryPage.setPriceFilter(maxFilterValue, false);
        softAssert.assertThat(categoryPage.setALlCategoryProducts()
                .isProductsPriceMatchWithFilter()).isTrue();
        softAssert.assertAll();
        return this;
    }
}
