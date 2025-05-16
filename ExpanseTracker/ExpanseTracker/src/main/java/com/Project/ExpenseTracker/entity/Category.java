package com.Project.ExpenseTracker.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Example: "Food", "Salary", "Rent"

    @Enumerated(EnumType.STRING)
    private CategoryType type; // EXPENSE or INCOME
    
    private Integer budgetLimit;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
