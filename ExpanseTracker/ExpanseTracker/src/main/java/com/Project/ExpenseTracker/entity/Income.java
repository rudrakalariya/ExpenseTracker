package com.Project.ExpenseTracker.entity;


import java.time.LocalDate;


import com.Project.ExpenseTracker.dto.IncomeDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data; 

@Entity
@Data
public class Income {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	
	private Integer amount;
	
	private LocalDate date;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	
	private String description;
	
	public IncomeDTO getIncomeDTO() {
		IncomeDTO incomeDTO = new IncomeDTO();
		
		incomeDTO.setId(id);
		incomeDTO.setTitle(title);
		incomeDTO.setAmount(amount);
		
		if (category != null) {
	        incomeDTO.setCategoryName(category.getName());
	        incomeDTO.setCategoryId(category.getId());
	    }
		
		incomeDTO.setDescription(description);
		incomeDTO.setDate(date);
		
		return incomeDTO;
		
		
		
}

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

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	
	
	

}
