package com.KaanIsmetOkul.Spendid.controller;

import com.KaanIsmetOkul.Spendid.entity.Expense;
import com.KaanIsmetOkul.Spendid.entity.Category;
import com.KaanIsmetOkul.Spendid.entity.User;
import com.KaanIsmetOkul.Spendid.exceptionHandling.CategoryNotFound;
import com.KaanIsmetOkul.Spendid.exceptionHandling.ResourceNotFound;
import com.KaanIsmetOkul.Spendid.security.CustomUserDetails;
import com.KaanIsmetOkul.Spendid.service.ExpenseService;
import com.KaanIsmetOkul.Spendid.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/expense")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<Expense>> getAllExpenses(
            @AuthenticationPrincipal CustomUserDetails userDetails) throws Exception {
        
        List<Expense> expenses = expenseService.getExpenses(userDetails.getId());
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/{category}")
    public ResponseEntity<List<Expense>> getExpensesByCategory(
            @PathVariable String category,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        
        try {
            Category categoryEnum = Category.valueOf(category.toUpperCase());
            List<Expense> expenses = expenseService.getExpenseByCategory(
                userDetails.getId(), 
                categoryEnum
            );
            return ResponseEntity.ok(expenses);
        } catch (IllegalArgumentException e) {
            throw new CategoryNotFound("Unable to find expenses with category: " + category);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expense> getExpenseById(
            @PathVariable UUID id,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        
        Expense expense = expenseService.getExpenseById(id, userDetails.getId());
        return ResponseEntity.ok(expense);
    }

    @PostMapping
    public ResponseEntity<Expense> saveExpense(
            @RequestBody Expense expense,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        
        try {
            // Get the full User entity from the service
            User user = userService.getUser(userDetails.getId());
            expense.setUser(user);
            
            Expense savedExpense = expenseService.saveExpense(expense);
            return new ResponseEntity<>(savedExpense, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            throw new ResourceNotFound("Unable to save expense: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(
            @PathVariable UUID id,
            @RequestBody Expense expenseDetails,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        
        try {
            // Get the full User entity from the service
            User user = userService.getUser(userDetails.getId());
            
            Expense expense = expenseService.updateExpense(id, expenseDetails, user);
            return ResponseEntity.ok(expense);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unable to update expense: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(
            @PathVariable UUID id,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        
        try {
            // Get the full User entity from the service
            User user = userService.getUser(userDetails.getId());
            
            expenseService.deleteExpense(id, user);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            throw new RuntimeException("Unable to delete expense: " + e.getMessage(), e);
        }
    }
}