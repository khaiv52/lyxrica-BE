package com.vanlang.lyxircaspb.request;

public class UpdateSecondLevelRequest {
    private String newName;
    private Long parentCategoryId;

    // Getters and Setters

    public UpdateSecondLevelRequest(String newName, Long parentCategoryId) {
        this.newName = newName;
        this.parentCategoryId = parentCategoryId;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public Long getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public UpdateSecondLevelRequest() {
    }
}