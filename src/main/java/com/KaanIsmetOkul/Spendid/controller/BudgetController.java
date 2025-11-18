package com.KaanIsmetOkul.Spendid.controller;

import com.KaanIsmetOkul.Spendid.dto.BudgetResponse;
import com.KaanIsmetOkul.Spendid.dto.CreateBudgetRequest;
import com.KaanIsmetOkul.Spendid.entity.Budget;
import com.KaanIsmetOkul.Spendid.entity.User;
import com.KaanIsmetOkul.Spendid.exceptionHandling.DuplicateBudgetException;
import com.KaanIsmetOkul.Spendid.repository.BudgetRepository;
import com.KaanIsmetOkul.Spendid.service.BudgetService;
import com.KaanIsmetOkul.Spendid.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/budgets")
public class BudgetController {

    @Autowired
    BudgetService budgetService;

    @Autowired
    BudgetRepository budgetRepository;

    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    public List<Budget> getBudgets() { return budgetRepository.findAll();}

    @PostMapping
    public ResponseEntity<BudgetResponse> createBudget(
            @Valid @RequestBody CreateBudgetRequest request,
            @AuthenticationPrincipal User user) {

        try {
            BudgetResponse response = budgetService.createBudget(request, user);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (DuplicateBudgetException e) {
            // Handle duplicate budget (same category/period)
            return ResponseEntity.status(HttpStatus.CONFLICT).build();  // 409 Conflict
        }
    }
}
