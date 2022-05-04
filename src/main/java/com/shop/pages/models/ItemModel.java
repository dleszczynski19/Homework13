package com.shop.pages.models;

import lombok.Data;

@Data
public class ItemModel {
    private String itemName;
    private String discountLabel;
    private Double productRegularPrice;
    private double productPrice;
    private int quantityAmount;
    private String productDescription;
    private String productSize;
    private String productColor;
    private String dimension;
    private boolean isCustomizable;

    public ItemModel() {
    }

    public ItemModel(String itemName, String discountLabel, Double productRegularPrice,
                     double productPrice, int quantityAmount, String productDescription,
                     String productSize, String productColor, String dimension, boolean isCustomizable) {
        this.itemName = itemName;
        this.discountLabel = discountLabel;
        this.productRegularPrice = productRegularPrice;
        this.productPrice = productPrice;
        this.quantityAmount = quantityAmount;
        this.productDescription = productDescription;
        this.productSize = productSize;
        this.productColor = productColor;
        this.dimension = dimension;
        this.isCustomizable = isCustomizable;
    }

    public void setQuantityAmount(int quantityAmount) {
        this.quantityAmount = quantityAmount;
    }

    @Override
    public String toString() {
        return itemName + "\n" + discountLabel + "\n" + productRegularPrice + "\n" + productPrice + "\n" + quantityAmount
                + "\n" + productDescription + "\n" + productSize + "\n" + productColor + "\n" + dimension +
                "\n--------------------------------------------------";
    }
}