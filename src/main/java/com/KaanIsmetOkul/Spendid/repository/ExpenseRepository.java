package com.KaanIsmetOkul.Spendid.repository;

import com.KaanIsmetOkul.Spendid.entity.Expense;
import com.KaanIsmetOkul.Spendid.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, UUID> {
    List<Expense> findByUser_Id(UUID userId);

    List<Expense> findByUser_IdAndCategory(UUID userId, Category category);

    @NonNull
    Optional<Expense> findById(UUID expenseId);

    @NonNull
    void deleteById(UUID expenseId);

    BigDecimal sumByUserAndCategoryAndDateBetween(UUID userId, Category category, LocalDate startDate, LocalDate endDate);
}