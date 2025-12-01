package com.KaanIsmetOkul.Spendid.controller;

import com.KaanIsmetOkul.Spendid.entity.Expense;
import com.KaanIsmetOkul.Spendid.entity.Category;
import com.KaanIsmetOkul.Spendid.entity.User;
import com.KaanIsmetOkul.Spendid.exceptionHandling.CategoryNotFound;
import com.KaanIsmetOkul.Spendid.exceptionHandling.ResourceNotFound;
import com.KaanIsmetOkul.Spendid.security.JwtTokenProvider;
import com.KaanIsmetOkul.Spendid.service.ExpenseService;
import com.KaanIsmetOkul.Spendid.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
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

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private User getCurrentUserFromToken(HttpServletRequest request) {
        try {
            String jwt = getJwtFromRequest(request);
            String username = jwtTokenProvider.getUsernameToken(jwt);
            return userService.getUser(username);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    @GetMapping
    public ResponseEntity<List<Expense>> getAllExpenses(HttpServletRequest request) {
        try {
            User currentUser = getCurrentUserFromToken(request);

            if (currentUser == null)
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

            List<Expense> expenses = expenseService.getExpenses(currentUser.getId());
            return ResponseEntity.ok(expenses);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Expense>> getExpensesByCategory(HttpServletRequest request, @PathVariable String category) {
        User currentUser = getCurrentUserFromToken(request);
        if (currentUser == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        try {
            Category categoryEnum = Category.valueOf(category.toUpperCase());
            List<Expense> expenses = expenseService.getExpenseByCategory(currentUser.getId(), categoryEnum);
            return ResponseEntity.ok(expenses);
        }
        catch (IllegalArgumentException e) {
            throw new CategoryNotFound("Unable to find expenses with category: " + category);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expense> getExpenseById(HttpServletRequest request, @PathVariable UUID uuid) {
        User currentUser = getCurrentUserFromToken(request);
        if (currentUser == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        Expense expense = expenseService.getExpenseById(uuid, currentUser.getId());
        return ResponseEntity.ok(expense);
    }

    @PostMapping
    public ResponseEntity<Expense> saveExpense(@RequestBody Expense expense, HttpServletRequest request) {
        try {

            User user = getCurrentUserFromToken(request);
            if (user == null)
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            expense.setUser(user);
            Expense savedExpense = expenseService.saveExpense(expense);
            return new ResponseEntity<>(savedExpense, HttpStatus.CREATED);
        }

        catch (IllegalArgumentException e) {
            throw new ResourceNotFound("Unable to find expense and userId");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(@RequestBody Expense expenseDetails, @PathVariable UUID id, HttpServletRequest request) {
        try {
            User user = getCurrentUserFromToken(request);
            if (user == null)
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

            Expense expense = expenseService.updateExpense(id, expenseDetails, user);
            return ResponseEntity.ok(expense);
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unable to find expense and userId");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable UUID id, HttpServletRequest request) {
        try {
            User user = getCurrentUserFromToken(request);
            if (user == null)
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            expenseService.deleteExpense(id, user);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }


}