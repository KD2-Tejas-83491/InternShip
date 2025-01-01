package com.sunbeam.dao;

import com.sunbeam.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDao extends JpaRepository<Category, Long> {
    Category findByName(String name); // To fetch a category by name, if needed.
}
