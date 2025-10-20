package com.KaanIsmetOkul.CreditFlux.entity;

public enum ExpenseCategory {
    DINING("Dining", "Restaurants, cafes, and food delivery"),
    TRAVEL("Travel", "Airlines, hotels, car rentals"),
    GROCERIES("Groceries", "Supermarkets and food stores"),
    GAS("Gas", "Gas station and fuel"),
    SHOPPING("Shopping", "E-commerce shopping and in-store shopping"),
    ENTERTAINMENT("Entertainment", "Movies, events, concerts and other entertainment"),
    OTHER("Other", "Miscellaneous expenses");

    private String displayName;
    private String description;

    ExpenseCategory(String displayName, String descripion) {
        this.displayName = displayName;
        this.description = descripion;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
