package com.KaanIsmetOkul.Spendid.dto;

import java.math.BigDecimal;

import com.KaanIsmetOkul.Spendid.entity.BudgetPeriod;
import jakarta.validation.constraints.Positive;
public class UpdateBudgetRequest {

    @Positive(message = "always must be positive")
    private BigDecimal amount;

    private BudgetPeriod period;

    public UpdateBudgetRequest() {}

    public UpdateBudgetRequest(BudgetPeriod period, BigDecimal amount) {
        this.period = period;
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BudgetPeriod getPeriod() {
        return period;
    }

    public void setPeriod(BudgetPeriod period) {
        this.period = period;
    }
}
