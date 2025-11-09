package com.KaanIsmetOkul.Spendid.service;

import com.KaanIsmetOkul.Spendid.entity.Expense;
import com.KaanIsmetOkul.Spendid.exceptionHandling.ExpenseNotFound;
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

    public List<Expense> saveExpenses(List<Expense> expenses) {return expenseRepository.saveAll(expenses); }

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

    public Expense getExpense(UUID id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new ExpenseNotFound("Unable to find expense with id: " + id));
    }

    public Expense updateExpense(Expense expenseDetails, UUID id) {
        Expense expense = getExpense(id);
        expense.setUser(expenseDetails.getUser());
        expense.setExpenseCategory(expenseDetails.getExpenseCategory());
        expense.setAmount(expenseDetails.getAmount());
        expense.setUpdatedAt(expenseDetails.getUpdatedAt());
        expense.setDate(expenseDetails.getDate());
        expense.setDescription(expenseDetails.getDescription());

        return expense;
    }

    public void deleteExpense(UUID id) {
        if (!expenseRepository.existsById(id))
            throw new ExpenseNotFound("Unable to find expense with id: " + id);
        expenseRepository.deleteById(id);
    }
}
