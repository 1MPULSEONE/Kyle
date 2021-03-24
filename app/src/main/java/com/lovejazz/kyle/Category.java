package com.lovejazz.kyle;

public class Category {
    private String name;
    private String linkToIcon;
    private String linkToBackgroundImage;
    private String id;

    public Category(String name, String linkToIcon, String linkToBackgroundImage, String id) {
        this.name = name;
        this.linkToIcon = linkToIcon;
        this.linkToBackgroundImage = linkToBackgroundImage;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getLinkToIcon() {
        return linkToIcon;
    }

    public String getLinkToBackgroundImage() {
        return linkToBackgroundImage;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLinkToIcon(String linkToIcon) {
        this.linkToIcon = linkToIcon;
    }

    public void setLinkToBackgroundImage(String linkToBackgroundImage) {
        this.linkToBackgroundImage = linkToBackgroundImage;
    }
}
