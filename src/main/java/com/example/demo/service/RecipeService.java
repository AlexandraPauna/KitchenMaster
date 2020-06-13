package com.example.demo.service;

import com.example.demo.model.Recipe;
import com.example.demo.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

public interface RecipeService {
    List<Recipe> getAllRecipes();
    Recipe saveRecipe(Recipe recipe);
    List<Recipe> getAllRecipesForLoggedUser(User user);
    Recipe findRecipeById(Integer id);
    Page<Recipe> getAllRecipesByCategoryPage(Integer category_id, Integer pageNumber, String sortKey);
}
