package com.sunbeam.service;

import com.sunbeam.entities.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Category createCategory(Category category);
    List<Category> getAllCategories();
    Optional<Category> getCategoryById(Long id);
    Category updateCategory(Long id, Category categoryDetails);
    void deleteCategory(Long id);
}
