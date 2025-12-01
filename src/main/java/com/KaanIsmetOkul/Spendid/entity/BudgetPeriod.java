package com.KaanIsmetOkul.Spendid.entity;


public enum BudgetPeriod {

    MONTHLY("Monthly", "Budget within the month"),
    WEEKLY("Weekly", "Budget within the week");

    private String display;
    private String description;

    BudgetPeriod(String display, String description) {
        this.display = display;
        this.description = description;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
