package com.Project.ExpenseTracker.services.income;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Project.ExpenseTracker.dto.IncomeDTO;
import com.Project.ExpenseTracker.entity.Category;
import com.Project.ExpenseTracker.entity.CategoryType;
import com.Project.ExpenseTracker.entity.Income;
import com.Project.ExpenseTracker.repository.IncomeRepository;
import com.Project.ExpenseTracker.services.category.CategoryService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IncomeServiceImpl implements IncomeService {
	
	private final IncomeRepository incomeRepository;
	@Autowired
    private CategoryService categoryService;
	
	public IncomeServiceImpl(IncomeRepository incomeRepository) {
		this.incomeRepository = incomeRepository;
	}
	
	public Income postIncome(IncomeDTO incomeDTO) {
		return saveOrUpdateIncome(new Income(), incomeDTO);
	}
	
	 private Income saveOrUpdateIncome(Income income, IncomeDTO incomeDTO) {
	        // 🔍 Fetch category by name
	        Category category = categoryService.getCategoryByName(incomeDTO.getCategoryName().trim())
	                .orElseThrow(() -> new EntityNotFoundException("Category not found: " + incomeDTO.getCategoryName()));

	        // ❌ Ensure only INCOME categories are used for income entries
	        if (category.getType() != CategoryType.INCOME) {
	            throw new EntityNotFoundException("Invalid category type! Incomes must have an INCOME category.");
	        }

	        // ✅ Save income entry
	        income.setTitle(incomeDTO.getTitle());
	        income.setDate(incomeDTO.getDate());
	        income.setAmount(incomeDTO.getAmount());
	        income.setDescription(incomeDTO.getDescription());
	        income.setCategory(category); 	

	        return incomeRepository.save(income);
	    }
	
	public Income updateIncome(Long id,IncomeDTO incomeDTO) {
		Optional<Income> optionalIncome = incomeRepository.findById(id);
		if(optionalIncome.isPresent()) {
			return saveOrUpdateIncome(optionalIncome.get(), incomeDTO);
		}else {
			throw new EntityNotFoundException("Income is not present with id "+id);
		}
	}
	
	public List<IncomeDTO> getAllIncomes(){
		return incomeRepository.findAll().stream()
				.sorted(Comparator.comparing(Income::getDate).reversed())
				.map(Income::getIncomeDTO)
				.collect(Collectors.toList());
		
	}
	
	public IncomeDTO getIncomeById(Long id) {
		Optional<Income>optionalIncome = incomeRepository.findById(id);
		if(optionalIncome.isPresent()) {
			return optionalIncome.get().getIncomeDTO();
		}else {
			throw new EntityNotFoundException("Income is not present with id "+id);
		}
			
	}
	
	public void deleteIncome(Long id) {
		Optional<Income> optionalIncome = incomeRepository.findById(id);
		if(optionalIncome.isPresent()) {
			incomeRepository.deleteById(id);
		}else {
			throw new EntityNotFoundException("Income is not present with id "+id);
		}
	}
}
