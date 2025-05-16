package com.Project.ExpenseTracker.services.expense;

import java.util.List;

import com.Project.ExpenseTracker.dto.ExpenseDTO;
import com.Project.ExpenseTracker.entity.Expense;

public interface ExpenseService {
	
	Expense postExpense(ExpenseDTO expenseDTO);
	
	List<Expense>getAllExpenses();
	
	Expense getExpenseById(Long id);
	
	 Expense updatExpense(Long id,ExpenseDTO expenseDTO);

	 void deleteExpense(Long id);
}
