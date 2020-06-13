package com.example.demo.service;

import com.example.demo.model.Category;
import com.example.demo.model.Recipe;
import com.example.demo.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface RecipeService {
    List<Recipe> getAllRecipes();
    Recipe saveRecipe(Recipe recipe);
    List<Recipe> getAllRecipesForLoggedUser(User user);
    Recipe findRecipeById(Integer id);
    void deleteById(int id);
    Recipe updateRecipe(Recipe recipe);
}
