package com.Project.ExpenseTracker.controller;

import com.Project.ExpenseTracker.dto.CategoryDTO;
import com.Project.ExpenseTracker.entity.Category;
import com.Project.ExpenseTracker.entity.CategoryType;
import com.Project.ExpenseTracker.services.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // ✅ Add a new category
  
    @PostMapping
    public Category addCategory(@RequestBody CategoryDTO categoryDTO) {
        return categoryService.saveCategory(categoryDTO);
    }

    // ✅ Get all categories (optionally filter by type)
    @GetMapping
    public List<Category> getCategories(@RequestParam(required = false) CategoryType type) {
        return categoryService.getCategories(type);
    }
}
