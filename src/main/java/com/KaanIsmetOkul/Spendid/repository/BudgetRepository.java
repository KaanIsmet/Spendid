package com.KaanIsmetOkul.Spendid.repository;

import com.KaanIsmetOkul.Spendid.entity.Budget;
import com.KaanIsmetOkul.Spendid.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BudgetRepository extends JpaRepository<Budget,UUID> {

    @NonNull
    Optional<Budget> findById(UUID budgetId);

    @NonNull
    Optional<Budget> findByIdAndUserId(UUID budgetId, UUID userId);

    @NonNull
    void deleteById(UUID budgetId);

    List<Budget> findByUserId(UUID userId);

    List<Budget> findByUserIdAndCategory(UUID userId, Category category);
}