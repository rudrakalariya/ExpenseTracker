package com.Project.ExpenseTracker.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class RecurringTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    
    private Integer amount;
    
    private LocalDate startDate; // First transaction date
    private LocalDate nextExecutionDate; // When it should be added next

    @Enumerated(EnumType.STRING)
    private FrequencyType frequency; // Daily, Weekly, Monthly, Yearly

    @Enumerated(EnumType.STRING)
    private CategoryType categoryType;// INCOME or EXPENSE 
    
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private String description;

    private boolean active = true; // User can deactivate it

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

	public LocalDate getNextExecutionDate() {
		return nextExecutionDate;
	}

	public void setNextExecutionDate(LocalDate nextExecutionDate) {
		this.nextExecutionDate = nextExecutionDate;
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
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
