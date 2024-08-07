package com.vanlang.lyxircaspb.request;

public class CreateCategoryRequest {
    private String topLevelCategory;
    private String secondLevelSectionName;
    private String thirdLevelItemName;
    private Integer topLevelCategoryLevel;
    private Integer secondLevelSectionLevel;
    private Integer thirdLevelItemLevel;

    public CreateCategoryRequest() {
    }

    public CreateCategoryRequest(String topLevelCategory, String secondLevelSectionName, String thirdLevelItemName, Integer topLevelCategoryLevel, Integer secondLevelSectionLevel, Integer thirdLevelItemLevel) {
        this.topLevelCategory = topLevelCategory;
        this.secondLevelSectionName = secondLevelSectionName;
        this.thirdLevelItemName = thirdLevelItemName;
        this.topLevelCategoryLevel = topLevelCategoryLevel;
        this.secondLevelSectionLevel = secondLevelSectionLevel;
        this.thirdLevelItemLevel = thirdLevelItemLevel;
    }

    public String getTopLevelCategory() {
        return topLevelCategory;
    }

    public void setTopLevelCategory(String topLevelCategory) {
        this.topLevelCategory = topLevelCategory;
    }

    public String getSecondLevelSectionName() {
        return secondLevelSectionName;
    }

    public void setSecondLevelSectionName(String secondLevelSectionName) {
        this.secondLevelSectionName = secondLevelSectionName;
    }

    public String getThirdLevelItemName() {
        return thirdLevelItemName;
    }

    public void setThirdLevelItemName(String thirdLevelItemName) {
        this.thirdLevelItemName = thirdLevelItemName;
    }

    public Integer getTopLevelCategoryLevel() {
        return topLevelCategoryLevel;
    }

    public void setTopLevelCategoryLevel(Integer topLevelCategoryLevel) {
        this.topLevelCategoryLevel = topLevelCategoryLevel;
    }

    public Integer getSecondLevelSectionLevel() {
        return secondLevelSectionLevel;
    }

    public void setSecondLevelSectionLevel(Integer secondLevelSectionLevel) {
        this.secondLevelSectionLevel = secondLevelSectionLevel;
    }

    public Integer getThirdLevelItemLevel() {
        return thirdLevelItemLevel;
    }

    public void setThirdLevelItemLevel(Integer thirdLevelItemLevel) {
        this.thirdLevelItemLevel = thirdLevelItemLevel;
    }
}
