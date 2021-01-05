package com.lovejazz.kyle;

public class Account {
    private String name;
    private String login;
    private int imageId;

    public Account(String name, String login, int imageId) {
        this.name = name;
        this.login = login;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public int getImageId() {
        return imageId;
    }

    //testAccounts
    public static final Account[] accounts = {
            new Account("Instagram", "@love_jazz", R.drawable.instagram_logo),
            new Account("Gmail", "parkhomenko2049@gmail.com", R.drawable.gmail_logo),
            new Account("Dropbox", "lovejazz", R.drawable.dropbox_logo),
            new Account("Telegram","@impulseone",R.drawable.telegram_logo),
            new Account("Github","1MPULSEONE",R.drawable.github_logo),
            new Account("YouTube","lovejazz",R.drawable.youtube_logo)
    };
}
