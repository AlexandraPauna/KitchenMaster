package com.example.demo.service.impl;

import com.example.demo.model.Category;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.model.Recipe;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(int id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);

        if (categoryOptional.isPresent())
            return categoryOptional.get();
        else
            throw new ResourceNotFoundException("category " + id + " not found");

    }

    @Override
    public Category saveCategory(Category category) {
        //Category savedCategory = categoryRepository.save(category);
        //return savedCategory;
        return categoryRepository.saveAndFlush(category);
    }

    @Override
    public void deleteById(int id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category findCategoryById(Integer id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);

        if(!categoryOptional.isPresent()){
            throw new  RuntimeException("Category not found!");
        }
        return categoryOptional.get();
    }

}
