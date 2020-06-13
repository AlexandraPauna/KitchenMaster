package com.example.demo.service.impl;

import com.example.demo.model.Category;
import com.example.demo.model.Recipe;
import com.example.demo.model.User;
import com.example.demo.repository.RecipeRepository;
import com.example.demo.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeServiceImpl implements RecipeService {

    private static final int PAGE_SIZE = 5;

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
    public List<Recipe> getAllRecipesForLoggedUser(User user) {
        return recipeRepository.findByUser(user);
    }

    @Override
    public Recipe findRecipeById(Integer id) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);

        if (!recipeOptional.isPresent()) {
            throw new RuntimeException("Recipe not found!");
        }

        return recipeOptional.get();
    }

    @Override
    public Page<Recipe> getAllRecipesByCategoryPage(Integer category_id, Integer pageNumber, String sortKey) {
//        PageRequest request =
//                new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.ASC, "date");
        Sort.Direction sortDirection = Sort.Direction.DESC;
        String sortField = "date";
        switch (sortKey){
            case "score":
                sortField = sortKey;
                sortDirection = Sort.Direction.DESC;
                break;
            case "caloriesAsc":
                sortField = "calories";
                sortDirection = Sort.Direction.ASC;
                break;
            case "caloriesDesc":
                sortField = "calories";
                sortDirection = Sort.Direction.DESC;
                break;
            case "titleAsc":
                sortField = "name";
                sortDirection = Sort.Direction.ASC;
                break;
            case "titleDesc":
                sortField = "name";
                sortDirection = Sort.Direction.DESC;
                break;
        }
        PageRequest request = PageRequest.of(pageNumber - 1, PAGE_SIZE, Sort.by(sortDirection,sortField));

        return recipeRepository.findByCategories_categoryId(category_id, request);
    }



    @Override
    public void deleteById(int id) {
        recipeRepository.deleteById(id);
    }

    @Override
    public Recipe updateRecipe(Recipe recipe) {

        return recipeRepository.saveAndFlush(recipe);
    }
}
