package com.KaanIsmetOkul.Spendid.dto;

import com.KaanIsmetOkul.Spendid.entity.BudgetPeriod;
import com.KaanIsmetOkul.Spendid.entity.BudgetStatus;
import com.KaanIsmetOkul.Spendid.entity.Category;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class BudgetStatusResponse {

    private UUID budgetId;
    private Category category;
    private BigDecimal budgetAmount;
    private BigDecimal spentAmount;
    private BigDecimal remainingAmount;
    private Double percentageUsed;
    private BudgetStatus status;
    private BudgetPeriod period;
    private LocalDate startDate;
    private LocalDate endDate;

    public BudgetStatusResponse() {}

    public BudgetStatusResponse(UUID budgetId, Category category,
                                BigDecimal budgetAmount, BigDecimal spentAmount,
                                BigDecimal remainingAmount, Double percentageUsed,
                                BudgetStatus status, BudgetPeriod period,
                                LocalDate startDate, LocalDate endDate) {
        this.budgetId = budgetId;
        this.category = category;
        this.budgetAmount = budgetAmount;
        this.spentAmount = spentAmount;
        this.remainingAmount = remainingAmount;
        this.percentageUsed = percentageUsed;
        this.status = status;
        this.period = period;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters and setters
    public UUID getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(UUID budgetId) {
        this.budgetId = budgetId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public BigDecimal getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(BigDecimal budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    public BigDecimal getSpentAmount() {
        return spentAmount;
    }

    public void setSpentAmount(BigDecimal spentAmount) {
        this.spentAmount = spentAmount;
    }

    public BigDecimal getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(BigDecimal remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public Double getPercentageUsed() {
        return percentageUsed;
    }

    public void setPercentageUsed(Double percentageUsed) {
        this.percentageUsed = percentageUsed;
    }

    public BudgetStatus getStatus() {
        return status;
    }

    public void setStatus(BudgetStatus status) {
        this.status = status;
    }

    public BudgetPeriod getPeriod() {
        return period;
    }

    public void setPeriod(BudgetPeriod period) {
        this.period = period;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}