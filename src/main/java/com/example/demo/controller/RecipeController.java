package com.example.demo.controller;

import com.example.demo.model.Recipe;
import com.example.demo.model.User;
import com.example.demo.service.CategoryService;
import com.example.demo.service.RecipeService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RecipeController {
    @Autowired
    RecipeService recipeService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/recipe/index", method = RequestMethod.GET)
    public String allRecipes(Model model) {
        model.addAttribute("recipes", recipeService.getAllRecipes());

        return "/recipe/index";
    }

    @RequestMapping(value = "/recipe/new", method = RequestMethod.GET)
    public String newRecipe(Model model) {
        model.addAttribute("categoriesList", categoryService.getAllCategories());
        Recipe recipe = new Recipe();
        recipe.setScore(0);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        recipe.setUser(user);
        model.addAttribute("recipe", recipe);

        return "/recipe/new";
    }

    @RequestMapping(value = "/recipe/new", method = RequestMethod.POST)
    public String savedRecipe(Model model) {


        return "/recipe/index";
    }
}
