package com.Project.ExpenseTracker.dto;



import java.util.List;

import com.Project.ExpenseTracker.entity.Expense;
import com.Project.ExpenseTracker.entity.Income;

import lombok.Data;

@Data
public class GraphDTO {
	private List<Expense> expenseList;
	private List<Income>incomeList;
	public List<Expense> getExpenseList() {
		return expenseList;
	}
	public void setExpenseList(List<Expense> expenseList) {
		this.expenseList = expenseList;
	}
	public List<Income> getIncomeList() {
		return incomeList;
	}
	public void setIncomeList(List<Income> incomeList) {
		this.incomeList = incomeList;
	}
}
