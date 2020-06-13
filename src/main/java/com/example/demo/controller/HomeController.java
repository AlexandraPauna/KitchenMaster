package com.example.demo.controller;

import com.example.demo.model.Category;
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

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    @Autowired
    RecipeService recipeService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    UserService userService;

    @RequestMapping(value={"/", "/home/index"}, method = RequestMethod.GET)
    public String allRecipes(Model model) {
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
        Collections.sort(categories, new Comparator<Category>() {
            @Override
            public int compare(Category c1, Category c2) {
                return c1.getName().compareTo(c2.getName());
            }
        });
        int nrPerColumn = categories.size() / 4;
        int surplus = categories.size() - (nrPerColumn * 4);
        int s = 0;
        int counter = 0;
        List<Category> list1 = null; List<Category> list2 = null; List<Category> list3 = null; List<Category> list4 = null;
        if (categories.size() > 0) {
            if (surplus > 0) {
                s = 1;
                surplus = surplus - 1;
            }
            list1 = categories.stream().limit(nrPerColumn + s).collect(Collectors.toList());
            counter = nrPerColumn + s;
            if (categories.size() > counter) {
                s = 0;
                if (surplus > 0) {
                    s = 1;
                    surplus = surplus - 1;
                }
                list2 = categories.stream().skip(counter).limit(nrPerColumn + s).collect(Collectors.toList()); //subCatgs.Skip(counter).Take(nrPerColumn + s).ToList();
                counter = counter + nrPerColumn + s;
                if (categories.size() > counter) {
                    s = 0;
                    if (surplus > 0) {
                        s = 1;
                        surplus = surplus - 1;
                    }
                    list3 = categories.stream().skip(counter).limit(nrPerColumn + s).collect(Collectors.toList()); //.Skip(counter).Take(nrPerColumn + s).ToList();
                    counter = counter + nrPerColumn + s;
                    if (categories.size() > counter) {
                        s = 0;
                        if (surplus > 0) {
                            s = 1;
                            surplus = surplus - 1;
                        }
                        list4 = categories.stream().skip(counter).limit(nrPerColumn + s).collect(Collectors.toList()); //Skip(counter).Take(nrPerColumn + s).ToList();
                    }

                }
            }
        }
        model.addAttribute("categoriesList1", list1);
        model.addAttribute("categoriesList2", list2);
        model.addAttribute("categoriesList3", list3);
        model.addAttribute("categoriesList4", list4);

        //sortare descendenta dupa data adaugarii
        List<Recipe> recipes = recipeService.getAllRecipes();
        Collections.sort(recipes, new Comparator<Recipe>() {
            @Override
            public int compare(Recipe r1, Recipe r2) {
                return r2.getDate().compareTo(r1.getDate());
            }
        });
        //ultimele 10 retete adaugate vor fi afisate
        model.addAttribute("latestRecipes", recipes.stream().limit(10).collect(Collectors.toList()));

        Calendar cal = Calendar.getInstance();
        Date currentDate = cal.getTime();
        model.addAttribute("currentDate", currentDate);

        return "/home/index";
    }

}
