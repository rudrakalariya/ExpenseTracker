package com.Project.ExpenseTracker.dto;

import com.Project.ExpenseTracker.entity.CategoryType;
import lombok.Data;

@Data
public class CategoryDTO {
    private String name;
    private CategoryType type;
    private Integer budgetLimit;
    
    
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public CategoryType getType() {
		return type;
	}
	public void setType(CategoryType type) {
		this.type = type;
	}
	public Integer getBudgetLimit() {
		return budgetLimit;
	}
	public void setBudgetLimit(Integer budgetLimit) {
		this.budgetLimit = budgetLimit;
	}
}
