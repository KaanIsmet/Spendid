package com.KaanIsmetOkul.Spendid.controller;

import com.KaanIsmetOkul.Spendid.entity.Expense;
import com.KaanIsmetOkul.Spendid.entity.Category;
import com.KaanIsmetOkul.Spendid.entity.User;
import com.KaanIsmetOkul.Spendid.exceptionHandling.CategoryNotFound;
import com.KaanIsmetOkul.Spendid.exceptionHandling.ResourceNotFound;
import com.KaanIsmetOkul.Spendid.repository.ExpenseRepository;
import com.KaanIsmetOkul.Spendid.repository.UserRepository;
import com.KaanIsmetOkul.Spendid.security.JwtTokenProvider;
import com.KaanIsmetOkul.Spendid.service.ExpenseService;
import com.KaanIsmetOkul.Spendid.service.UserService;
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
    private ExpenseService expenseService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @GetMapping("/expense")
    public List<Expense> getAllExpenses() {
        return expenseService.getAllExpenses();
    }

    @GetMapping("/expense/user/{id}")
    public List<Expense> getExpensesById(@PathVariable UUID id) throws Exception {
        return expenseService.getExpenses(id);
    }

    @GetMapping("/expense/user/{id}/category/{category}")
    public List<Expense> getExpensesByCategory(@PathVariable UUID userId, @PathVariable String category) {
        try {
            Category categories = Category.valueOf(category.toUpperCase());

        }
        catch (IllegalArgumentException e) {
            throw new CategoryNotFound("Unable to find the category for expense");
        }
    }

    @PostMapping("/expense/user/{id}")
    public ResponseEntity<Expense> saveExpense(@RequestBody Expense expense, @PathVariable UUID id) {
        try {

            User user = userService.getUser(id);
            expense.setUser(user);
            Expense savedExpense = expenseService.saveExpense(expense);
            return new ResponseEntity<>(savedExpense, HttpStatus.CREATED);
        }

        catch (IllegalArgumentException e) {
            throw new ResourceNotFound("Unable to find expense and userId");
        }
    }

    @PutMapping("/expense/user/{id}")
    public ResponseEntity<Expense> updateExpense(@RequestBody Expense expenseDetails, @PathVariable UUID id) {
        try {
            Expense expense = expenseService.updateExpense(expenseDetails, id);
            return ResponseEntity.ok(expense);
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unable to find expense and userId");
        }
    }

    @DeleteMapping("/expense/{id}")
    public ResponseEntity<?> deleteExpense(@PathVariable UUID id) {
        try {
            expenseService.deleteExpense(id);
            return ResponseEntity.ok("Successfully deleted expense: " + id);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}