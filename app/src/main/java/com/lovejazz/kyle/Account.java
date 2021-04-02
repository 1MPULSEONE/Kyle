package com.lovejazz.kyle;

public class Account {
    private String name;
    private String email;
    private String iconLink;
    private String id;

    public Account(String name, String email, String iconLink, String id) {
        this.name = name;
        this.email = email;
        this.iconLink = iconLink;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getIconLink() {
        return iconLink;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
