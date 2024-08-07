package com.vanlang.lyxircaspb.request;

public class CreateFeaturedRequest {

    private String name;

    private String href;

    private String imageSrc;

    private String imageAlt;

    private Long categoryId;

    public CreateFeaturedRequest(String name, String href, String imageSrc, String imageAlt, Long categoryId) {
        this.name = name;
        this.href = href;
        this.imageSrc = imageSrc;
        this.imageAlt = imageAlt;
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public String getImageAlt() {
        return imageAlt;
    }

    public void setImageAlt(String imageAlt) {
        this.imageAlt = imageAlt;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
