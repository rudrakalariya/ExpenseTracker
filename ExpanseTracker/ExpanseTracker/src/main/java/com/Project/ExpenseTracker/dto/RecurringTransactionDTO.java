package com.Project.ExpenseTracker.dto;

import com.Project.ExpenseTracker.entity.CategoryType;
import com.Project.ExpenseTracker.entity.FrequencyType; 
import lombok.Data;

import java.time.LocalDate;

@Data
public class RecurringTransactionDTO {
    private Long id;
    private String title;
    private Integer amount;
    private LocalDate startDate;
    private FrequencyType frequency;
    private CategoryType categoryType;
    private String categoryName; // ✅ Accept category name instead of ID
    private String description;
    private boolean active = true;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public FrequencyType getFrequency() {
		return frequency;
	}
	public void setFrequency(FrequencyType frequency) {
		this.frequency = frequency;
	}
	public CategoryType getCategoryType() {
		return categoryType;
	}
	public void setCategoryType(CategoryType categoryType) {
		this.categoryType = categoryType;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
}
