package com.example.demo.service;

import com.example.demo.model.Recipe;
import org.springframework.stereotype.Service;

import java.util.List;

public interface RecipeService {
    List<Recipe> getAllRecipes();
    Recipe saveRecipe(Recipe recipe);
}
