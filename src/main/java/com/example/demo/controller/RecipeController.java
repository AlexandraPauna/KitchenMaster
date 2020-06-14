package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.service.CategoryService;
import com.example.demo.service.RatingService;
import com.example.demo.service.RecipeService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Controller
public class RecipeController {
    @Autowired
    RecipeService recipeService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    UserService userService;

    @Autowired
    RatingService ratingService;

    private Map<String, Category> categoryCache;

    @RequestMapping(value = "/recipe/index", method = RequestMethod.GET)
    public String allRecipes(Model model) {
        model.addAttribute("recipes", recipeService.getAllRecipes());

        return "/recipe/index";
    }

    @RequestMapping(value = "/recipe/add", method = RequestMethod.GET)
    public String newRecipe(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        if(user != null){
            model.addAttribute("loggedUser", user);
            model.addAttribute("isAuth", "true");
            String role = user.getRoles().stream().findFirst().get().getRole().toUpperCase();
            model.addAttribute("role", role);
        }
        else{
            model.addAttribute("isAuth", "false");
        }

        List<Category> categories = categoryService.getAllCategories();
        categoryCache = new HashMap<String, Category>();
        for (Category category : categories) {
            categoryCache.put(category.getCategoryId().toString(), category);
        }
        model.addAttribute("categoriesList", categoryService.getAllCategories());

        Recipe recipe = new Recipe();
        model.addAttribute("recipe", recipe);

        return "/recipe/add";
    }

    @RequestMapping(value = "/recipe/add", method = RequestMethod.POST)
    public String savedRecipe(@Valid Recipe recipe, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            if(recipe.getCategories() != null){
                model.addAttribute("categories", recipe.getCategories());
            }
            model.addAttribute("categoriesList", categoryService.getAllCategories());

            return "/recipe/add";
        }
        recipe.setScore((double) 0);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        recipe.setUser(user);
        Calendar cal = Calendar.getInstance();
        Date dateAdded = cal.getTime();
        recipe.setDate(dateAdded);

        Recipe savedRecipe = recipeService.saveRecipe(recipe);

        return "redirect:/recipe/personal";
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) throws Exception {
        binder.registerCustomEditor(Set.class, "categories", new CustomCollectionEditor(Set.class) {
            protected Object convertElement(Object element) {
                if (element instanceof Category) {
                    System.out.println("Converting from Category to Category: " + element);
                    return element;
                }
                if (element instanceof String) {
                    Category category = categoryCache.get(element);
                    System.out.println("Looking up category for id " + element + ": " + category);
                    return category;
                }
                System.out.println("Don't know what to do with: " + element);
                return null;
            }
        });
    }

    @RequestMapping(value = "/recipe/personal", method = RequestMethod.GET)
    public String allRecipesForLoggedUser(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        if(user != null){
            model.addAttribute("loggedUser", user);
            model.addAttribute("isAuth", "true");
            String role = user.getRoles().stream().findFirst().get().getRole().toUpperCase();
            model.addAttribute("role", role);

            List<Recipe> recipes = recipeService.getAllRecipesForLoggedUser(user);
            model.addAttribute("recipes", recipes);
            model.addAttribute("nrOfRecipes", recipes.size());

            List<Rating> ratings = ratingService.getAllRatingsForLoggedUser(user);
            model.addAttribute("nrOfRatings", ratings.size());

            return "/recipe/personal";
        }
        else{
            model.addAttribute("isAuth", "false");
            return "redirect:/home/index";
        }
    }

    @GetMapping("/recipe/show/{id}")
    public String showRecipe(@PathVariable String id, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        if(user != null){
            model.addAttribute("loggedUser", user);
            model.addAttribute("isAuth", "true");
            String role = user.getRoles().stream().findFirst().get().getRole().toUpperCase();
            model.addAttribute("role", role);
        }
        else{
            model.addAttribute("isAuth", "false");
        }

        model.addAttribute("recipe", recipeService.findRecipeById(Integer.valueOf(id)));

        return "recipe/show";
    }

    @RequestMapping("recipe/{id}/delete")
    public String deleteById(@PathVariable String id){
        recipeService.deleteById(Integer.valueOf(id));
        return "redirect:/recipe/personal";
    }

    @RequestMapping(value = "/recipe/update/{id}", method = RequestMethod.GET)
    public String updateRecipe(Model model,@PathVariable int id) {
        List<Category> categories = categoryService.getAllCategories();
        categoryCache = new HashMap<String, Category>();
        for (Category category : categories) {
            categoryCache.put(category.getCategoryId().toString(), category);
        }
        model.addAttribute("categoriesList", categoryService.getAllCategories());

        Recipe recipe = recipeService.findRecipeById(id);
        model.addAttribute("recipe", recipe);
        return "/recipe/update";
    }

    @PostMapping(value = "/recipe/update/{id}")
    public String updateRecipe(@PathVariable("id") int id,@Valid Recipe recipe,
                                 BindingResult result, Model model) {
        Recipe currentRecipe = recipeService.findRecipeById(id);
        currentRecipe.setName(recipe.getName());
        currentRecipe.setScore(recipe.getScore());
        //Calendar cal = Calendar.getInstance();
        //Date dateUpdated = cal.getTime();
        //currentRecipe.setDate(dateUpdated);
        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //User user = userService.findUserByUserName(auth.getName());
        //String role = user.getRoles().stream().findFirst().get().getRole().toUpperCase();
        //currentRecipe.setUser(user);
        currentRecipe.setDescription(recipe.getDescription());
        currentRecipe.setPreparation_time(recipe.getPreparation_time());
        currentRecipe.setCalories(recipe.getCalories());
        currentRecipe.setInfo(recipe.getInfo());
        currentRecipe.setServing(recipe.getServing());
        currentRecipe.setCategories(recipe.getCategories());

        recipeService.updateRecipe(currentRecipe);
        if (result.hasErrors()){
            return "/recipe/update";
        }


        allRecipes(model);
        //model.addAttribute("role",role);
        return "redirect:/recipe/personal";

    }
}
