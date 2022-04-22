package com.shop.steps;

import com.shop.pages.CategoryPage;
import com.shop.pages.HeaderPage;
import org.openqa.selenium.WebDriver;

public class CategoryStep extends HeaderPage {
    private CategoryPage categoryPage;

    public CategoryStep(WebDriver driver, CategoryPage categoryPage) {
        super(driver);
        this.categoryPage = categoryPage;
    }

    public CategoryStep goToRandomItem() {
        categoryPage.isOnCategoryPage(null);
        categoryPage.setALlCategoryProducts();
        clickOnElement(categoryPage.getAllProductsList()
                .get(getRandomNumber(0, categoryPage.getAllProductsList().size() - 1))
                .getProductLink());
        return this;
    }
}
