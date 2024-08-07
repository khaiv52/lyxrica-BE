package com.vanlang.lyxircaspb.request;

public class UpdateThirdLevelRequest {
    private String newName;
    private Long parentSectionId;

    public UpdateThirdLevelRequest(Long parentSectionId, String newName) {
        this.parentSectionId = parentSectionId;
        this.newName = newName;
    }

    public UpdateThirdLevelRequest() {
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public Long getParentSectionId() {
        return parentSectionId;
    }

    public void setParentSectionId(Long parentSectionId) {
        this.parentSectionId = parentSectionId;
    }
}