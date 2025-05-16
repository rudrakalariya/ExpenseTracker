package com.Project.ExpenseTracker.repository;

import com.Project.ExpenseTracker.entity.RecurringTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface RecurringTransactionRepository extends JpaRepository<RecurringTransaction, Long> {
    List<RecurringTransaction> findByNextExecutionDateAndActive(LocalDate date, boolean active);
}
