package com.Project.ExpenseTracker.services.expense;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Project.ExpenseTracker.dto.ExpenseDTO;
import com.Project.ExpenseTracker.entity.Category;
import com.Project.ExpenseTracker.entity.CategoryType;
import com.Project.ExpenseTracker.entity.Expense;
import com.Project.ExpenseTracker.repository.ExpenseRepository;
import com.Project.ExpenseTracker.services.category.CategoryService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {
	
	private final ExpenseRepository expenseRepository;
	
	@Autowired
    public ExpenseServiceImpl(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }
	
	  @Autowired
	    private CategoryService categoryService;
	
	public Expense postExpense(ExpenseDTO expenseDTO) {
		return saveOrUpdateExpense(new Expense(),expenseDTO);
	}
	
	private Expense saveOrUpdateExpense(Expense expense, ExpenseDTO expenseDTO) {
	    // 🔍 Fetch category by name (case insensitive)
	    Category category = categoryService.getCategoryByName(expenseDTO.getCategoryName().trim())
	            .orElseThrow(() -> new EntityNotFoundException("Category not found: " + expenseDTO.getCategoryName()));

	    // ❌ Ensure only EXPENSE categories are used for expenses
	    if (category.getType() != CategoryType.EXPENSE) {
	        throw new EntityNotFoundException("Invalid category type! Expenses must have an EXPENSE category.");
	    }

	    // ✅ Check if budget limit is exceeded
	    int totalSpent = expenseRepository.sumAmountByCategory(category.getId());
	    if (category.getBudgetLimit() != null && (totalSpent + expenseDTO.getAmount()) > category.getBudgetLimit()) {
	        throw new EntityNotFoundException("Warning! You have exceeded your budget for " + category.getName());
	    }

	    // ✅ Save expense
	    expense.setTitle(expenseDTO.getTitle());
	    expense.setDate(expenseDTO.getDate());
	    expense.setAmount(expenseDTO.getAmount());
	    expense.setDescription(expenseDTO.getDescription());
	    expense.setCategory(category);

	    return expenseRepository.save(expense);
	}


	
	public Expense updatExpense(Long id,ExpenseDTO expenseDTO) {
		Optional<Expense> optionalExpense = expenseRepository.findById(id);
		if(optionalExpense.isPresent()) {
			return saveOrUpdateExpense(optionalExpense.get(), expenseDTO);
		}else {
			throw new EntityNotFoundException("Expense is not present with id "+id);
		}
	}
	
	public List<Expense>getAllExpenses(){
		return expenseRepository.findAll().stream().sorted(Comparator.comparing(Expense::getDate).reversed())
				.collect(Collectors.toList());
	}
	
	public Expense getExpenseById(Long id) {
		Optional<Expense> optionalExpense = expenseRepository.findById(id);
		if(optionalExpense.isPresent()) {
			return optionalExpense.get();
		}else {
			throw new EntityNotFoundException("Expense is not present with id "+ id);
		}
	}
	
	public void deleteExpense(Long id) {
		Optional<Expense>optionalExpense = expenseRepository.findById(id);
		if(optionalExpense.isPresent()) {
			expenseRepository.deleteById(id);
		}else {
			throw new EntityNotFoundException("Expense is not present with id "+id);
		}
	}
	
	
	
}
