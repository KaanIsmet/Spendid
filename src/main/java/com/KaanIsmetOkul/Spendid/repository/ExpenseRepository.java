package com.KaanIsmetOkul.Spendid.repository;

import com.KaanIsmetOkul.Spendid.entity.Expense;
import com.KaanIsmetOkul.Spendid.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    // Fix: Convert LocalDate to LocalDateTime for comparison
    @Query("SELECT e FROM Expense e WHERE e.user.id = :userId " +
            "AND e.category = :category " +
            "AND CAST(e.date AS LocalDate) BETWEEN :startDate AND :endDate")
    List<Expense> findByUserIdAndCategoryAndDateBetween(
            @Param("userId") UUID userId,
            @Param("category") Category category,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    // Fix: Sum query with LocalDate conversion
    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e " +
            "WHERE e.user.id = :userId " +
            "AND e.category = :category " +
            "AND CAST(e.date AS LocalDate) BETWEEN :startDate AND :endDate")
    BigDecimal sumByUserAndCategoryAndDateBetween(
            @Param("userId") UUID userId,
            @Param("category") Category category,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}