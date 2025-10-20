package com.KaanIsmetOkul.CreditFlux.entity;

public enum CardRewardCategory {
    DINING("Dining"),
    TRAVEL("Travel"),
    GROCERIES("Groceries"),
    GAS("Gas"),
    ENTERTAINMENT("Entertainment"),
    ONLINE_SHOPPING("Online Shopping"),
    GENERAL("General"),
    OTHER("Other");

    private final String displayName;

    CardRewardCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
