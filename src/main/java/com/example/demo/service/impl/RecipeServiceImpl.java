package com.example.demo.service.impl;

import com.example.demo.model.Recipe;
import com.example.demo.repository.RecipeRepository;
import com.example.demo.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    @Override
    public Recipe saveRecipe(Recipe recipe) {
        return recipeRepository.saveAndFlush(recipe);
    }

    @Override
    public void deleteById(int id) {
        recipeRepository.deleteById(id);
    }
}
