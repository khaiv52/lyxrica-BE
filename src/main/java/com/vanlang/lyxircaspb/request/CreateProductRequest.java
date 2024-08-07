package com.vanlang.lyxircaspb.request;

import com.vanlang.lyxircaspb.model.Size;

import java.util.HashSet;
import java.util.Set;

public class CreateProductRequest {
    private String title;
    private String description;
    private int price;

    private int discountedPrice;

    private int discountPercent;

    private int quantity;

    private String brand;

    private String color;

    private Set<Size> sizes = new HashSet<>();

    private String imageUrl;

    // Tên danh mục cấp 1
    private String topLevelCategory;

    // Tên danh mục cấp 2 (section)
    private String secondLevelCategory;

    // Tên danh mục cấp 3 (Item)
    private String thirdLevelCategory;

    public CreateProductRequest(String title, String description, int price, int discountedPrice, int discountPercent, int quantity, String brand, String color, Set<Size> sizes, String imageUrl, String topLevelCategory, String secondLevelCategory, String thirdLevelCategory) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.discountedPrice = discountedPrice;
        this.discountPercent = discountPercent;
        this.quantity = quantity;
        this.brand = brand;
        this.color = color;
        this.sizes = sizes;
        this.imageUrl = imageUrl;
        this.topLevelCategory = topLevelCategory;
        this.secondLevelCategory = secondLevelCategory;
        this.thirdLevelCategory = thirdLevelCategory;
    }

    public CreateProductRequest() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(int discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Set<Size> getSizes() {
        return sizes;
    }

    public void setSizes(Set<Size> sizes) {
        this.sizes = sizes;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTopLevelCategory() {
        return topLevelCategory;
    }

    public void setTopLevelCategory(String topLevelCategory) {
        this.topLevelCategory = topLevelCategory;
    }

    public String getSecondLevelCategory() {
        return secondLevelCategory;
    }

    public void setSecondLevelCategory(String secondLevelCategory) {
        this.secondLevelCategory = secondLevelCategory;
    }

    public String getThirdLevelCategory() {
        return thirdLevelCategory;
    }

    public void setThirdLevelCategory(String thirdLevelCategory) {
        this.thirdLevelCategory = thirdLevelCategory;
    }
}
