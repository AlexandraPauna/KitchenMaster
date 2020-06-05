package com.example.demo.service;

import com.example.demo.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    public Category findById(int id);
    Category saveCategory(Category category);
    void deleteById(int id);
}
