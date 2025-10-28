package com.KaanIsmetOkul.Spendid.service;

import com.KaanIsmetOkul.Spendid.entity.Expense;
import com.KaanIsmetOkul.Spendid.exceptionHandling.UserNotFound;
import com.KaanIsmetOkul.Spendid.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public Expense saveExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    public List<Expense> getExpenses(UUID userId) throws Exception {
        try {
            List<Expense> expenses = expenseRepository.findByUser_Id(userId);
            return expenses;
        }
        catch (UserNotFound e) {
            throw new UserNotFound("Unable to get expenses with user id: " + userId);
        }
        catch (Exception e) {
            throw new Exception("Unable to retrieve any expenses");
        }
    }
}
