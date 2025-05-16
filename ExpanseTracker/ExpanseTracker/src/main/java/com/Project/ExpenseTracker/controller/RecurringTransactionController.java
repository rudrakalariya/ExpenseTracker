package com.Project.ExpenseTracker.controller;

import com.Project.ExpenseTracker.dto.RecurringTransactionDTO;
import com.Project.ExpenseTracker.entity.RecurringTransaction;
import com.Project.ExpenseTracker.services.recurring.RecurringTransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recurring-transactions")
@CrossOrigin("*")
public class RecurringTransactionController {

    private final RecurringTransactionService recurringTransactionService;

    public RecurringTransactionController(RecurringTransactionService recurringTransactionService) {
        this.recurringTransactionService = recurringTransactionService;
    }

    // ✅ Accepts categoryName instead of categoryId  
    @PostMapping
    public ResponseEntity<?> saveRecurringTransaction(@RequestBody RecurringTransactionDTO dto) {
        RecurringTransaction savedTransaction = recurringTransactionService.saveRecurringTransaction(dto);
        return ResponseEntity.ok(savedTransaction);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRecurringTransaction(@PathVariable Long id, @RequestBody RecurringTransactionDTO dto) {
        RecurringTransaction updatedTransaction = recurringTransactionService.updateRecurringTransaction(id, dto);
        return ResponseEntity.ok(updatedTransaction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRecurringTransaction(@PathVariable Long id) {
        recurringTransactionService.deleteRecurringTransaction(id);
        return ResponseEntity.ok("Recurring transaction deleted successfully.");
    }

    
}