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
public interface ExpenseRepository extends JpaRepository<Expense, Long>{
	
	
	List<Expense>findByDateBetween(LocalDate startDate,LocalDate endDate);
	
	@Query("SELECT SUM(e.amount) FROM Expense e")
	Double sumAllAmounts();
	
	Optional<Expense> findFirstByOrderByDateDesc();
	
	@Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.category.id = :categoryId")
	int sumAmountByCategory(@Param("categoryId") Long categoryId);

	@Modifying
    @Transactional 
    @Query("DELETE FROM Expense e WHERE e.category.id = :categoryId AND e.date >= :fromDate")
    void deleteFutureExpenses(@Param("categoryId") Long categoryId, @Param("fromDate") LocalDate fromDate);

    //  Update future expenses with new amount and title
    @Modifying
    @Transactional
    @Query("UPDATE Expense e SET e.amount = :newAmount WHERE e.category.id = :categoryId AND e.date >= :fromDate")
    void updateFutureExpenses(@Param("categoryId") Long categoryId, 
                              @Param("newAmount") Integer newAmount, 
                              @Param("fromDate") LocalDate fromDate);
	
}
