package com.KaanIsmetOkul.Spendid.service;


import com.KaanIsmetOkul.Spendid.dto.BudgetResponse;
import com.KaanIsmetOkul.Spendid.dto.BudgetStatusResponse;
import com.KaanIsmetOkul.Spendid.dto.CreateBudgetRequest;
import com.KaanIsmetOkul.Spendid.dto.UpdateBudgetRequest;
import com.KaanIsmetOkul.Spendid.entity.*;
import com.KaanIsmetOkul.Spendid.exceptionHandling.BudgetNotFound;
import com.KaanIsmetOkul.Spendid.exceptionHandling.UserNotFound;
import com.KaanIsmetOkul.Spendid.repository.BudgetRepository;
import com.KaanIsmetOkul.Spendid.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private ExpenseService expenseService;

    // 1. Create budget
    public BudgetResponse createBudget(CreateBudgetRequest request, User user) {
        // - Calculate start/end dates based on period
        // - Check for duplicate budget (same user/category/period)
        // - Create Budget entity
        // - Save to database
        // - Return BudgetResponse
        BudgetPeriod period = request.getPeriod();
        LocalDate startDate = calculateStartDate(period);
        LocalDate endDate = calculateEndDate(period);

        Budget budget = new Budget(user, request.getAmount(), request.getCategory(), period, startDate, endDate);
        budgetRepository.save(budget);

        return convertToResponse(budget);
    }

    // 2. Get all budgets for user
    public List<BudgetResponse> getUserBudgets(UUID userId) {
        // - Find all budgets by user
        // - Convert to BudgetResponse list
        // - Return
        try {
            List<Budget> budgets = budgetRepository.findByUserId(userId);
            List<BudgetResponse> responses = new ArrayList<>();
            for (Budget budget : budgets) {
                responses.add(convertToResponse(budget));
            }
            return responses;
        }
        catch (UserNotFound userNotFound) {
            throw new UserNotFound("Unable to find budgets with user id: " + userId);
        }
    }

    // 3. Get single budget by ID
    public BudgetResponse getBudgetById(UUID budgetId, User user) {
        // - Find budget by ID
        // - Verify ownership (user can only see their own budgets)
        // - Convert to BudgetResponse
        // - Return
        try {
            Optional<Budget> optionalBudget = budgetRepository.findByIdAndUserId(budgetId, user.getId());
            if (optionalBudget.isEmpty())
                throw new BudgetNotFound("Unable to find budget");
            else {
                Budget budget = optionalBudget.get();
                return convertToResponse(budget);
            }
        }
        catch (UserNotFound userNotFound) {
            throw new UserNotFound("Unable to find user");
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new IllegalArgumentException("Illegal arguments have been used");
        }
    }

    // 4. Update budget
    public BudgetResponse updateBudget(UUID budgetId, UpdateBudgetRequest request, User user) {
        // - Find budget by ID
        // - Verify ownership
        // - Update amount and/or period
        // - Recalculate start/end dates if period changed
        // - Save
        // - Return BudgetResponse

        try {
            Optional<Budget> budgetOptional = budgetRepository.findByIdAndUserId(budgetId, user.getId());
            if (budgetOptional.isEmpty())
                throw new BudgetNotFound("Unable to find budget with id: " + budgetId);
            else {
                Budget budget = budgetOptional.get();
                budget.setAmount(request.getAmount());
                if (budget.getPeriod() != request.getPeriod()) {
                    LocalDate startDate = calculateStartDate(request.getPeriod());
                    LocalDate endDate = calculateEndDate(request.getPeriod());
                    budget.setPeriod(request.getPeriod());
                    budget.setStartDate(startDate);
                    budget.setEndDate(endDate);
                }

                return convertToResponse(budget);

            }
        }
        catch (BudgetNotFound budgetNotFound) {
            throw new BudgetNotFound("Unable to find budget with id: " + budgetId);
        }
    }

    // 5. Delete budget
    public void deleteBudget(UUID budgetId, User user) {
        // - Find budget by ID
        // - Verify ownership
        // - Delete (soft delete or hard delete)
        Optional<Budget> budgetOptional = budgetRepository.findByIdAndUserId(budgetId, user.getId());
        if (budgetOptional.isEmpty())
            throw new BudgetNotFound("Unable to find budget with id: " + budgetId);
        else {
            budgetRepository.deleteById(budgetId);
        }
    }

    // 6. Get budget status (THE KEY FEATURE)
    public BudgetStatusResponse getBudgetStatus(UUID budgetId, User user) {
        // - Find budget by ID
        // - Verify ownership
        // - Query expenses: sum amount WHERE category = budget.category AND date BETWEEN start/end
        // - Calculate: remaining = budgetAmount - spent
        // - Calculate: percentage = (spent / budgetAmount) * 100
        // - Determine status: OK (<80%), WARNING (80-100%), EXCEEDED (>100%)
        // - Return BudgetStatusResponse

        try {
            Budget budget = budgetRepository.findByIdAndUserId(budgetId, user.getId())
                    .orElseThrow(() -> new BudgetNotFound("Unable to find budget with id: " + budgetId));
            BigDecimal spentAmount = expenseService.calculateSumAmount(user.getId(), budget.getCategory(), budget.getStartDate(), budget.getEndDate());
            if (spentAmount == null) {
                spentAmount = BigDecimal.ZERO;
            }

            BigDecimal remainingAmount = budget.getAmount().subtract(spentAmount);

            double percentageUsed = 0.0;
            if (budget.getAmount().compareTo(BigDecimal.ZERO) > 0) {
                percentageUsed = spentAmount
                        .divide(budget.getAmount(), 4, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(100))
                        .doubleValue();
            }

            BudgetStatus status = calculateStatus(percentageUsed);

            return new BudgetStatusResponse(
                    budget.getId(),
                    budget.getCategory(),
                    budget.getAmount(),
                    spentAmount,
                    remainingAmount,
                    percentageUsed,
                    status,
                    budget.getPeriod(),
                    budget.getStartDate(),
                    budget.getEndDate()
            );

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    // 7. Get all budget statuses for user
    public List<BudgetStatusResponse> getAllBudgetStatuses(User user) {
        // - Get all user's budgets
        // - For each budget, calculate status (like method #6)
        // - Return list of BudgetStatusResponse
        List<Budget> budgets = user.getBudgets();
        if (budgets.isEmpty())
            throw new BudgetNotFound("The list of budgets for user is empty.");
        else {
            List<BudgetStatusResponse> responses = new ArrayList<>();
            for (Budget budget : budgets) {
                BigDecimal spentAmount = expenseService.calculateSumAmount(user.getId(), budget.getCategory(), budget.getStartDate(), budget.getEndDate());
                if (spentAmount == null) {
                    spentAmount = BigDecimal.ZERO;
                }

                BigDecimal remainingAmount = budget.getAmount().subtract(spentAmount);

                double percentageUsed = 0.0;
                if (budget.getAmount().compareTo(BigDecimal.ZERO) > 0) {
                    percentageUsed = spentAmount
                            .divide(budget.getAmount(), 4, RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(100))
                            .doubleValue();
                }

                BudgetStatus status = calculateStatus(percentageUsed);

                responses.add(new BudgetStatusResponse(
                        budget.getId(),
                        budget.getCategory(),
                        budget.getAmount(),
                        spentAmount,
                        remainingAmount,
                        percentageUsed,
                        status,
                        budget.getPeriod(),
                        budget.getStartDate(),
                        budget.getEndDate()
                ));
            }
            return responses;
        }
    }

    public BudgetResponse convertToResponse(Budget budget) {
        return new BudgetResponse(
                budget.getId(),
                budget.getCategory(),
                budget.getAmount(),
                budget.getPeriod(),
                budget.getStartDate(),
                budget.getEndDate(),
                budget.getCreatedAt(),
                budget.getUpdatedAt()
        );
    }

    public LocalDate calculateStartDate(BudgetPeriod period) {
        LocalDate now = LocalDate.now();
        return switch (period) {
            case BudgetPeriod.MONTHLY -> now.withDayOfMonth(1);
            case BudgetPeriod.WEEKLY -> now.with(DayOfWeek.MONDAY);
        };
    }

    public LocalDate calculateEndDate(BudgetPeriod period) {
        LocalDate now = LocalDate.now();
        return switch (period) {
            case BudgetPeriod.MONTHLY -> now.withDayOfMonth(now.lengthOfMonth());
            case BudgetPeriod.WEEKLY -> now.with(DayOfWeek.MONDAY);
        };
    }

    public  BudgetStatus calculateStatus(double percentage) {
        if (percentage >= 100.0) {
            return BudgetStatus.EXCEEDED;
        }
        else if (percentage >= 80.0) {
            return BudgetStatus.WARNING;
        }
        else
            return BudgetStatus.OK;
    }
}
