package com.shop.steps;

import com.shop.pages.CategoryPage;
import com.shop.pages.HeaderPage;
import com.shop.pages.HomePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FilterStep extends HeaderPage {
    private static HomePage homePage;

    public FilterStep(WebDriver driver) {
        super(driver);
        homePage = new HomePage(driver);
    }

    public boolean isPriceMatchWithFilter(int minFilterValue, int maxFilterValue) {
        WebElement category = homePage.getArtCategory();
        String categoryName = category.getText();

        homePage.goToCategory(category);
        CategoryPage categoryPage = new CategoryPage(driver, category, categoryName);
        categoryPage.isOnCategoryPage(null);
        categoryPage.setPriceFilter(minFilterValue, true);
        categoryPage.setPriceFilter(maxFilterValue, false);
        return categoryPage.setALlCategoryProducts()
                .isProductsPriceMatchWithFilter();
    }
}
