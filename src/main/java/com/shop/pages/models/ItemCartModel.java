package com.shop.pages.models;

import com.shop.pages.HeaderPage;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

public class ItemCartModel extends HeaderPage {
    private List<ItemModel> itemsList = new ArrayList<>();

    public ItemCartModel(WebDriver driver) {
        super(driver);
    }

    public void addItem(ItemModel item){
            itemsList.add(item);
    }

    public List<ItemModel> getItemsList() {
        return itemsList;
    }
}