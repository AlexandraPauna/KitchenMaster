package com.example.demo.repository;

import com.example.demo.model.Category;
import com.example.demo.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
