package com.sunbeam.service;

import com.sunbeam.dao.CategoryDao;
import com.sunbeam.entities.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public Category createCategory(Category category) {
        return categoryDao.save(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryDao.findAll();
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
        return categoryDao.findById(id);
    }

    @Override
    public Category updateCategory(Long id, Category categoryDetails) {
        return categoryDao.findById(id).map(category -> {
            category.setName(categoryDetails.getName());
            return categoryDao.save(category);
        }).orElse(null);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryDao.deleteById(id);
    }
}
