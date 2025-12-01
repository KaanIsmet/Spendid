package com.KaanIsmetOkul.Spendid.dto;

import com.KaanIsmetOkul.Spendid.entity.BudgetPeriod;
import com.KaanIsmetOkul.Spendid.entity.Category;
import jakarta.validation.constraints.Positive;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;

public class CreateBudgetRequest {

    @NonNull
    private Category category;

    @NonNull
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    @NonNull
    private BudgetPeriod period;

    public CreateBudgetRequest() {}

    public CreateBudgetRequest(@NonNull Category category, @NonNull BigDecimal amount, @NonNull BudgetPeriod period) {
        this.category = category;
        this.amount = amount;
        this.period = period;
    }

    @NonNull
    public Category getCategory() {
        return category;
    }

    public void setCategory(@NonNull Category category) {
        this.category = category;
    }

    @NonNull
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(@NonNull BigDecimal amount) {
        this.amount = amount;
    }

    @NonNull
    public BudgetPeriod getPeriod() {
        return period;
    }

    public void setPeriod(@NonNull BudgetPeriod period) {
        this.period = period;
    }
}
