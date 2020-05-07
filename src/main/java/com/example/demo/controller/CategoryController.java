package com.example.demo.controller;

import com.example.demo.service.CategoryService;
import com.example.demo.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @RequestMapping(value = "/category/index", method = RequestMethod.GET)
    public String allCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "/category/index";
    }
}
