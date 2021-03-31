package com.lovejazz.kyle;

public class Account {
    private String name;
    private String email;
    private String iconLink;

    public Account(String name, String email, String iconLink) {
        this.name = name;
        this.email = email;
        this.iconLink = iconLink;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIconLink(String iconLink) {
        this.iconLink = iconLink;
    }
}
