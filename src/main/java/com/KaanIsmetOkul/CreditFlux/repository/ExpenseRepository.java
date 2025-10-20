package com.KaanIsmetOkul.CreditFlux.repository;

import com.KaanIsmetOkul.CreditFlux.entity.Expense;
import com.KaanIsmetOkul.CreditFlux.entity.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, UUID> {
    List<Expense> findByUser_Id(UUID userId);

    List<Expense> findByUser_IdAndExpenseCategory(UUID userId, ExpenseCategory expenseCategory);
}
