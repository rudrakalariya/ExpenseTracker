package com.Project.ExpenseTracker.repository;

import com.Project.ExpenseTracker.entity.Category;
import com.Project.ExpenseTracker.entity.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByType(CategoryType type);
    boolean existsByName(String name); // ✅ Check if category name exists
    Optional<Category> findByNameIgnoreCase(String name);
}
