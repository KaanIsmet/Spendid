package com.KaanIsmetOkul.CreditFlux.controller;

import com.KaanIsmetOkul.CreditFlux.entity.Expense;
import com.KaanIsmetOkul.CreditFlux.entity.ExpenseCategory;
import com.KaanIsmetOkul.CreditFlux.exceptionHandling.CategoryNotFound;
import com.KaanIsmetOkul.CreditFlux.repository.ExpenseRepository;
import com.KaanIsmetOkul.CreditFlux.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class ExpenseController {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/expense")
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    @GetMapping("/expense/user/{id}")
    public List<Expense> getExpensesById(@PathVariable UUID id) {
        return expenseRepository.findByUser_Id(id);
    }

    @GetMapping("/expense/user/{id}/category/{category}")
    public List<Expense> getExpensesByCategory(@PathVariable UUID userId, @PathVariable String category) {
        try {
            ExpenseCategory expenseCategory = ExpenseCategory.valueOf(category.toUpperCase());
            return expenseRepository.findByUser_IdAndExpenseCategory(userId, expenseCategory);
        }
        catch (IllegalArgumentException e) {
            throw new CategoryNotFound("Unable to find the category for expense");
        }
    }
    public ResponseEntity<Expense> saveExpense(@RequestBody Expense expense) {
        Expense savedExpense = expenseService.saveExpense(expense);
        return new ResponseEntity<>(savedExpense, HttpStatus.CREATED);
    }
}
