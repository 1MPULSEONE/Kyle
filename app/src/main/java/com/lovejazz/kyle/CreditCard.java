package com.lovejazz.kyle;

public class CreditCard {
    private String name;
    private String logoName;
    private int gradientId;

    public String getName() {
        return name;
    }

    public String getLogoName() {
        return logoName;
    }

    public int getGradientId() {
        return gradientId;
    }

    public CreditCard(String name, String logoName, int gradientId) {
        this.name = name;
        this.logoName = logoName;
        this.gradientId = gradientId;

    }

    //testCards
    public static final CreditCard[] creditCards = {
            new CreditCard("Visa Card", "Visa", R.drawable.gradient_background_1),
            new CreditCard("Mother`s card", "MasterCard", R.drawable.gradient_background_2),
            new CreditCard("Debit card", "Visa", R.drawable.gradient_background_3)
    };
}
