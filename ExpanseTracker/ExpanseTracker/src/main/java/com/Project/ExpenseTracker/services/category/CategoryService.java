package com.Project.ExpenseTracker.services.category;

import com.Project.ExpenseTracker.dto.CategoryDTO;
import com.Project.ExpenseTracker.entity.Category;
import com.Project.ExpenseTracker.entity.CategoryType;
import com.Project.ExpenseTracker.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    
    public Category saveCategory(CategoryDTO categoryDTO) {
        if (categoryRepository.existsByName(categoryDTO.getName())) {
            throw new IllegalArgumentException("Category with this name already exists.");
        }
        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setType(categoryDTO.getType());
        category.setBudgetLimit(categoryDTO.getBudgetLimit());
        return categoryRepository.save(category);
    }


    public List<Category> getCategories(CategoryType type) {
        if (type != null) {
            return categoryRepository.findByType(type);
        }
        return categoryRepository.findAll();
    }
    

    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found with ID: " + categoryId));
    }
    
    public Optional<Category> getCategoryByName(String name) {
        return categoryRepository.findByNameIgnoreCase(name); 
    }


}
