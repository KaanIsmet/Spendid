package com.KaanIsmetOkul.Spendid.service;


import com.KaanIsmetOkul.Spendid.dto.BudgetResponse;
import com.KaanIsmetOkul.Spendid.dto.BudgetStatusResponse;
import com.KaanIsmetOkul.Spendid.dto.CreateBudgetRequest;
import com.KaanIsmetOkul.Spendid.dto.UpdateBudgetRequest;
import com.KaanIsmetOkul.Spendid.entity.Budget;
import com.KaanIsmetOkul.Spendid.entity.BudgetPeriod;
import com.KaanIsmetOkul.Spendid.entity.User;
import com.KaanIsmetOkul.Spendid.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

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
    public List<BudgetResponse> getUserBudgets(User user) {
        // - Find all budgets by user
        // - Convert to BudgetResponse list
        // - Return
    }

    // 3. Get single budget by ID
    public BudgetResponse getBudgetById(UUID budgetId, User user) {
        // - Find budget by ID
        // - Verify ownership (user can only see their own budgets)
        // - Convert to BudgetResponse
        // - Return
    }

    // 4. Update budget
    public BudgetResponse updateBudget(UUID budgetId, UpdateBudgetRequest request, User user) {
        // - Find budget by ID
        // - Verify ownership
        // - Update amount and/or period
        // - Recalculate start/end dates if period changed
        // - Save
        // - Return BudgetResponse
    }

    // 5. Delete budget
    public void deleteBudget(UUID budgetId, User user) {
        // - Find budget by ID
        // - Verify ownership
        // - Delete (soft delete or hard delete)
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
    }

    // 7. Get all budget statuses for user
    public List<BudgetStatusResponse> getAllBudgetStatuses(User user) {
        // - Get all user's budgets
        // - For each budget, calculate status (like method #6)
        // - Return list of BudgetStatusResponse
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
}
