package com.KaanIsmetOkul.Spendid.controller;

import com.KaanIsmetOkul.Spendid.dto.BudgetResponse;
import com.KaanIsmetOkul.Spendid.dto.CreateBudgetRequest;
import com.KaanIsmetOkul.Spendid.dto.UpdateBudgetRequest;
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
import org.springframework.security.access.method.P;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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

    @GetMapping
    public List<Budget> getBudgets() { return budgetRepository.findAll();}

    @GetMapping("/{id}")
    public List<Budget> getBudgets(@PathVariable UUID id) {
        return budgetRepository.findByUserId(id);
    }



    @PostMapping
    public ResponseEntity<BudgetResponse> createBudget(
            @RequestBody CreateBudgetRequest request,
            Principal principal
    ) {

        String username = principal.getName();
        User user = userService.getUser(username);

        BudgetResponse response = budgetService.createBudget(request, user);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BudgetResponse> updateBudget(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateBudgetRequest request,
            @AuthenticationPrincipal User user) {

        BudgetResponse response = budgetService.updateBudget(id, request, user);
        return ResponseEntity.ok(response);
    }

    // Delete budget
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBudget(
            @PathVariable UUID id,
            @AuthenticationPrincipal User user) {

        budgetService.deleteBudget(id, user);
        return ResponseEntity.noContent().build();  // 204 No Content
    }

}
