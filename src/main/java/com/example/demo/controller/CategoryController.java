package com.example.demo.controller;

import com.example.demo.model.Category;
import com.example.demo.model.Recipe;
import com.example.demo.model.User;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.CategoryService;
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
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.*;

@Controller
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/category/index", method = RequestMethod.GET)
    public String allCategories(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        String role = user.getRoles().stream().findFirst().get().getRole().toUpperCase();

        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("role",role);
        return "/category/index";
    }

    @RequestMapping(value = "/category/new", method = RequestMethod.GET)
    public String newCategory(Model model) {
        Category category = new Category();
        model.addAttribute("category", category);
        return "/category/new";
    }

    @RequestMapping(value = "/category/new", method = RequestMethod.POST)
    public String savedCategory(@Valid Category category, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "error";
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());

        Category savedCategory = categoryService.saveCategory(category);
        allCategories(model);
        return "/category/index";
    }

    @RequestMapping(value = "/category/update/{id}", method = RequestMethod.GET)
    public String updateCategory(Model model,@PathVariable int id) {
        Category category = categoryService.findById(id);;
        model.addAttribute("category", category);
        return "/category/update";
    }

    /*
    @RequestMapping(value= "/category/update/{id}", method= RequestMethod.PUT)
    public Category updateCategory(@RequestBody Category updCategory, @PathVariable int id) throws Exception {
        System.out.println(this.getClass().getSimpleName() + " - Update employee details by id is invoked.");

        Category category =  categoryService.findById(id);
        //if (!category.isPresent())
            //throw new Exception("Could not find employee with id- " + id);

        /* IMPORTANT - To prevent the overriding of the existing value of the variables in the database,
         * if that variable is not coming in the @RequestBody annotation object. */
        /*if(updCategory.getName() == null || updCategory.getName().isEmpty())
            updCategory.setName(category.getName());

        // Required for the "where" clause in the sql query template.
        //updCategory.setId(id);
        return categoryService.updateCategory(updCategory);
    }*/

    @PostMapping(value = "/category/update/{id}")
    public String updateCategory(@PathVariable("id") int id,@Valid Category category,
                                 BindingResult result, Model model) {
        Category currentCategory = categoryService.findById(id);
        currentCategory.setName(category.getName());
        categoryService.updateCategory(currentCategory);
        if (result.hasErrors()){
            return "/category/update";
        }
        categoryService.updateCategory(currentCategory);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        String role = user.getRoles().stream().findFirst().get().getRole().toUpperCase();

        allCategories(model);
        model.addAttribute("role",role);
        return "redirect:/category/index";

    }


    @RequestMapping("category/{id}/delete")
    public String deleteById(@PathVariable String id){
        categoryService.deleteById(Integer.valueOf(id));
        return "redirect:/category/index";
    }

}
