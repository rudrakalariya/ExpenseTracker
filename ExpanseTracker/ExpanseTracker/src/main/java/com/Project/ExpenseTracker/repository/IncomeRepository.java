package com.Project.ExpenseTracker.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Project.ExpenseTracker.entity.Expense;
import com.Project.ExpenseTracker.entity.Income;

import jakarta.transaction.Transactional;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {
	
	List<Income>findByDateBetween(LocalDate startDate,LocalDate endDate);
	
	@Query("SELECT SUM(i.amount) FROM Income i")
	Double sumAllAmounts();
	
	Optional<Income> findFirstByOrderByDateDesc();
	
	@Modifying
    @Transactional
    @Query("DELETE FROM Income i WHERE i.category.id = :categoryId AND i.date >= :fromDate")
    void deleteFutureIncomes(@Param("categoryId") Long categoryId, @Param("fromDate") LocalDate fromDate);

    // ✅ Update future incomes with new amount and title
    @Modifying
    @Transactional
    @Query("UPDATE Income i SET i.amount = :newAmount WHERE i.category.id = :categoryId AND i.date >= :fromDate")
    void updateFutureIncomes(@Param("categoryId") Long categoryId, 
                             @Param("newAmount") Integer newAmount, 
                             @Param("fromDate") LocalDate fromDate); 
}

