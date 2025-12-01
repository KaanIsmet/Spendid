package com.KaanIsmetOkul.Spendid.service;

import com.KaanIsmetOkul.Spendid.entity.Category;
import com.KaanIsmetOkul.Spendid.entity.Expense;
import com.KaanIsmetOkul.Spendid.entity.User;
import com.KaanIsmetOkul.Spendid.exceptionHandling.ExpenseNotFound;
import com.KaanIsmetOkul.Spendid.exceptionHandling.ResourceNotFound;
import com.KaanIsmetOkul.Spendid.exceptionHandling.UnauthorizedException;
import com.KaanIsmetOkul.Spendid.exceptionHandling.UserNotFound;
import com.KaanIsmetOkul.Spendid.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
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

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
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

    public Expense getExpense(UUID id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new ExpenseNotFound("Unable to find expense with id: " + id));
    }

    public Expense updateExpense(UUID expenseId, Expense expenseDetails, User currentUser) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new ResourceNotFound("Expense not found with id: " + expenseId));

        // Security check: only owner can update
        if (!expense.getUser().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException("You can only update your own expenses");
        }

        if (expenseDetails.getDescription() != null) {
            expense.setDescription(expenseDetails.getDescription());
        }
        if (expenseDetails.getAmount() != null) {
            expense.setAmount(expenseDetails.getAmount());
        }
        if (expenseDetails.getCategory() != null) {
            expense.setCategory(expenseDetails.getCategory());
        }
        if (expenseDetails.getDate() != null) {
            expense.setDate(expenseDetails.getDate());
        }

        return expenseRepository.save(expense);
    }

    public void deleteExpense(UUID expenseId, User currentUser) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new ResourceNotFound("Expense not found with id: " + expenseId));

        // Security check: only owner can delete
        if (!expense.getUser().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException("You can only delete your own expenses");
        }

        expenseRepository.delete(expense);
    }

    public BigDecimal calculateSumAmount(UUID userId, Category category, LocalDate startDate, LocalDate endDate) {
        return expenseRepository.sumByUserAndCategoryAndDateBetween(userId, category, startDate, endDate);
    }

    public List<Expense> getExpenseByCategory(UUID uuid, Category category) {
        return expenseRepository.findByUser_IdAndCategory(uuid, category);
    }

    public Expense getExpenseById(UUID expenseId, UUID userId) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new ExpenseNotFound("Unable to find expense with id: " + expenseId));

        if (!expense.getUser().getId().equals(userId))
            throw new UnauthorizedException("You can only access your own expenses");

        return expense;
    }
}
