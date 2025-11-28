package com.KaanIsmetOkul.Spendid.controller;

import com.KaanIsmetOkul.Spendid.dto.BudgetResponse;
import com.KaanIsmetOkul.Spendid.dto.BudgetStatusResponse;
import com.KaanIsmetOkul.Spendid.dto.CreateBudgetRequest;
import com.KaanIsmetOkul.Spendid.dto.UpdateBudgetRequest;
import com.KaanIsmetOkul.Spendid.entity.Budget;
import com.KaanIsmetOkul.Spendid.entity.BudgetStatus;
import com.KaanIsmetOkul.Spendid.entity.User;
import com.KaanIsmetOkul.Spendid.exceptionHandling.DuplicateBudgetException;
import com.KaanIsmetOkul.Spendid.repository.BudgetRepository;
import com.KaanIsmetOkul.Spendid.security.CustomUserDetails;
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
    UserService userService;


    @GetMapping
    public ResponseEntity<List<BudgetResponse>> getBudgets(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<BudgetResponse> budgets = budgetService.getUserBudgets(userDetails.getId());
        return ResponseEntity.ok(budgets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BudgetResponse> getBudgets(@PathVariable UUID id,
                                   @AuthenticationPrincipal CustomUserDetails userDetails) {
        BudgetResponse budgetResponse = budgetService.getBudgetById(id, userDetails);
        return ResponseEntity.ok(budgetResponse);
    }



    @PostMapping
    public ResponseEntity<BudgetResponse> createBudget(
            @RequestBody CreateBudgetRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {

        if (userDetails == null) {
            System.out.println("ERROR: User is null in createBudget!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        BudgetResponse response = budgetService.createBudget(request, userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BudgetResponse> updateBudget(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateBudgetRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        BudgetResponse response = budgetService.updateBudget(id, request, userDetails);
        return ResponseEntity.ok(response);
    }

    // Delete budget
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBudget(
            @PathVariable UUID id,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        budgetService.deleteBudget(id, userDetails);
        return ResponseEntity.noContent().build();  // 204 No Content
    }

    @GetMapping("/status")
    public ResponseEntity<List<BudgetStatusResponse>> getAllBudgetStatus(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<BudgetStatusResponse> statuses = budgetService.getAllBudgetStatuses(userDetails);
        return ResponseEntity.ok(statuses);
    }

    @GetMapping("/status/{id}")
    public ResponseEntity<BudgetStatusResponse> getBudgetStatus(@PathVariable UUID id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        BudgetStatusResponse response = budgetService.getBudgetStatus(id, userDetails);
        return ResponseEntity.ok(response);
    }

}
