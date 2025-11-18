package com.KaanIsmetOkul.Spendid.entity;

public enum BudgetStatus {

    OK("OK", "Amount of expenses is well within the budget"),
    WARNING("WARNING", "Amount of expenses is approaching the budget"),
    EXCEEDED("EXCEEDED", "Amount of expenses has exceeded the budget");

    private String displayName;
    private String description;

    BudgetStatus(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
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
