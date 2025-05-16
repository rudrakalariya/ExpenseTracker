package com.Project.ExpenseTracker.services.recurring;

import com.Project.ExpenseTracker.dto.RecurringTransactionDTO;
import com.Project.ExpenseTracker.entity.*;
import com.Project.ExpenseTracker.repository.RecurringTransactionRepository;
import com.Project.ExpenseTracker.repository.ExpenseRepository;
import com.Project.ExpenseTracker.repository.IncomeRepository;
import com.Project.ExpenseTracker.services.category.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecurringTransactionService { 

    private final RecurringTransactionRepository recurringTransactionRepository;
    private final ExpenseRepository expenseRepository;
    private final IncomeRepository incomeRepository;
    private final CategoryService categoryService; // Inject CategoryService

    public RecurringTransactionService(RecurringTransactionRepository recurringTransactionRepository,
                                       ExpenseRepository expenseRepository,
                                       IncomeRepository incomeRepository,
                                       CategoryService categoryService) {
        this.recurringTransactionRepository = recurringTransactionRepository;
        this.expenseRepository = expenseRepository;
        this.incomeRepository = incomeRepository;
        this.categoryService = categoryService;
    }
 
    // ✅ Save Recurring Transaction (Accept DTO)
    public RecurringTransaction saveRecurringTransaction(RecurringTransactionDTO dto) {
        // 🔍 Fetch category by name
        Category category = categoryService.getCategoryByName(dto.getCategoryName().trim())
                .orElseThrow(() -> new EntityNotFoundException("Category not found: " + dto.getCategoryName()));

        // 🛠️ Convert DTO to Entity
        RecurringTransaction transaction = new RecurringTransaction();
        transaction.setTitle(dto.getTitle());
        transaction.setAmount(dto.getAmount());
        transaction.setStartDate(dto.getStartDate());
        transaction.setFrequency(dto.getFrequency());
        transaction.setActive(dto.isActive()); 
        transaction.setCategory(category);
        transaction.setCategoryType(category.getType()); // Use category type (EXPENSE/INCOME)
        transaction.setNextExecutionDate(dto.getStartDate().isBefore(LocalDate.now()) ? LocalDate.now() : dto.getStartDate());

        transaction.setDescription(dto.getDescription());

        return recurringTransactionRepository.save(transaction);
    }

    @Scheduled(cron = "0 * * * * ?") // Runs every minute (for testing)
    public void processRecurringTransactions() {
        System.out.println("⏳ Running scheduled job at: " + LocalDateTime.now());

        List<RecurringTransaction> transactions = recurringTransactionRepository.findByNextExecutionDateAndActive(LocalDate.now(), true);

        if (transactions.isEmpty()) {
            System.out.println("⚠️ No recurring transactions found for today.");
        } else {
            for (RecurringTransaction transaction : transactions) {
                System.out.println("🔄 Processing recurring transaction: " + transaction.getTitle());

                if (transaction.getCategoryType() == CategoryType.EXPENSE) {
                    Expense expense = new Expense();
                    expense.setTitle(transaction.getTitle());
                    expense.setAmount(transaction.getAmount());
                    expense.setDate(LocalDate.now());
                    expense.setCategory(transaction.getCategory());
                    expense.setDescription(transaction.getDescription());
                    expenseRepository.save(expense);
                    System.out.println("✅ Expense saved: " + expense);
                } else if (transaction.getCategoryType() == CategoryType.INCOME) {
                    Income income = new Income();
                    income.setTitle(transaction.getTitle());
                    income.setAmount(transaction.getAmount());
                    income.setDate(LocalDate.now());
                    income.setCategory(transaction.getCategory());
                    income.setDescription(transaction.getDescription());
                    incomeRepository.save(income);
                    System.out.println("✅ Income saved: " + income);
                }

                // Update next execution date
                LocalDate nextDate = calculateNextExecutionDate(transaction.getNextExecutionDate(), transaction.getFrequency());
                transaction.setNextExecutionDate(nextDate);
                recurringTransactionRepository.save(transaction);
                System.out.println("⏭️ Next execution date updated to: " + nextDate);
            }
        }
    }


    // ✅ Calculate Next Execution Date
    private LocalDate calculateNextExecutionDate(LocalDate lastExecutionDate, FrequencyType frequency) {
        switch (frequency) {
            case DAILY: return lastExecutionDate.plusDays(1);
            case WEEKLY: return lastExecutionDate.plusWeeks(1);
            case MONTHLY: return lastExecutionDate.plusMonths(1);
            case YEARLY: return lastExecutionDate.plusYears(1);
            default: throw new IllegalArgumentException("Unknown frequency");
        }
    }

    
    public void deleteRecurringTransaction(Long id) {
        RecurringTransaction transaction = recurringTransactionRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Recurring transaction not found"));

        // Delete future transactions
        if (transaction.getCategoryType() == CategoryType.EXPENSE) {
            expenseRepository.deleteFutureExpenses(transaction.getCategory().getId(), transaction.getNextExecutionDate());
        } else if (transaction.getCategoryType() == CategoryType.INCOME) {
            incomeRepository.deleteFutureIncomes(transaction.getCategory().getId(), transaction.getNextExecutionDate());
        }

        // Delete recurring transaction itself
        recurringTransactionRepository.deleteById(id);
    }

    public RecurringTransaction updateRecurringTransaction(Long id, RecurringTransactionDTO dto) {
        RecurringTransaction transaction = recurringTransactionRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Recurring transaction not found"));

        // Update details
        transaction.setTitle(dto.getTitle());
        transaction.setAmount(dto.getAmount());
        transaction.setDescription(dto.getDescription());
        transaction.setFrequency(dto.getFrequency());
        transaction.setNextExecutionDate(LocalDate.now()); // Reset to today

        // Apply changes to future transactions
        updateFutureTransactions(transaction);

        return recurringTransactionRepository.save(transaction);
    }

    // ✅ Update future transactions
    private void updateFutureTransactions(RecurringTransaction transaction) {
        if (transaction.getCategoryType() == CategoryType.EXPENSE) {
            expenseRepository.updateFutureExpenses(transaction.getCategory().getId(),
                transaction.getAmount(), transaction.getNextExecutionDate());
        } else if (transaction.getCategoryType() == CategoryType.INCOME) {
            incomeRepository.updateFutureIncomes(transaction.getCategory().getId(),
                transaction.getAmount(), transaction.getNextExecutionDate());
        } 
    }


}
