package com.KaanIsmetOkul.CreditFlux.service;

import com.KaanIsmetOkul.CreditFlux.entity.Expense;
import com.KaanIsmetOkul.CreditFlux.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public Expense saveExpense(Expense expense) {
        return expenseRepository.save(expense);
    }
}
