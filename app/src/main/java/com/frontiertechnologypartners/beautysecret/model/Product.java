package com.frontiertechnologypartners.beautysecret.model;

import java.io.Serializable;

public class Product implements Serializable {
    private String productName,productColor,productPrice,image,productId;
    public Product() {
    }

    public Product(String productName, String productColor, String productPrice, String image, String productId) {
        this.productName = productName;
        this.productColor = productColor;
        this.productPrice = productPrice;
        this.image = image;
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductColor() {
        return productColor;
    }

    public void setProductColor(String productColor) {
        this.productColor = productColor;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
