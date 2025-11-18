package com.KaanIsmetOkul.Spendid.repository;

import com.KaanIsmetOkul.Spendid.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;
import java.util.UUID;

public interface BudgetRepository extends JpaRepository<Budget,UUID> {

    @NonNull
    Optional<Budget> findById(UUID budgetId);
}