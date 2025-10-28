package com.KaanIsmetOkul.Spendid.controller;

import com.KaanIsmetOkul.Spendid.entity.Expense;
import com.KaanIsmetOkul.Spendid.entity.ExpenseCategory;
import com.KaanIsmetOkul.Spendid.entity.User;
import com.KaanIsmetOkul.Spendid.exceptionHandling.CategoryNotFound;
import com.KaanIsmetOkul.Spendid.exceptionHandling.ResourceNotFound;
import com.KaanIsmetOkul.Spendid.exceptionHandling.UserNotFound;
import com.KaanIsmetOkul.Spendid.repository.ExpenseRepository;
import com.KaanIsmetOkul.Spendid.repository.UserRepository;
import com.KaanIsmetOkul.Spendid.service.ExpenseService;
import com.KaanIsmetOkul.Spendid.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class ExpenseController {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private UserService userService;

    @GetMapping("/expense")
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    @GetMapping("/expense/user/{id}")
    public List<Expense> getExpensesById(@PathVariable UUID id) throws Exception {
        return expenseService.getExpenses(id);
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

//    @PutMapping("/expense/user/{id}")
//    public ResponseEntity<Expense> updateExpense(@RequestBody Expense expense, @PathVariable UUID id) {
//        try {
//            User user = userService.getUser(id);
//
//        }
//    }
}
