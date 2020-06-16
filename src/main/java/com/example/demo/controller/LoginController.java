package com.example.demo.controller;

import com.example.demo.model.Rating;
import com.example.demo.model.Recipe;
import com.example.demo.model.User;
import com.example.demo.service.RatingService;
import com.example.demo.service.RecipeService;
import com.example.demo.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    RecipeService recipeService;

    @Autowired
    RatingService ratingService;

    private static final Logger logger = Logger.getLogger(LoginController.class);

    @GetMapping(value="/login")
    public ModelAndView login(){
        if(logger.isDebugEnabled()){
            logger.debug("login is executed!");
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @PostMapping(value="/login")
    public ModelAndView loggedUser(){
        if(logger.isDebugEnabled()){
            logger.debug("loggedUser is executed!");
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }

    @GetMapping(value="/registration")
    public ModelAndView registration(){
        if(logger.isDebugEnabled()){
            logger.debug("registration is executed!");
        }
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @PostMapping(value = "/registration")
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult){
        if(logger.isDebugEnabled()){
            logger.debug("createNewUser is executed!");
        }
        ModelAndView modelAndView= new ModelAndView();
        User userExists = userService.findUserByUserName(user.getUserName());
        if(userExists != null){
            bindingResult.rejectValue("userName", "error.user",
                    "There is already a user registered with the user name provided");
        }
        if(bindingResult.hasErrors()){
            modelAndView.setViewName("registration");
        }
        else{
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("registration");
        }

        return modelAndView;
    }

    @GetMapping(value="/admin/home")
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        modelAndView.addObject("userName", "Welcome " + user.getUserName() + "/" + user.getFirstName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
        modelAndView.setViewName("admin/home");

        return modelAndView;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String showProfile(Model model) {
        if(logger.isDebugEnabled()){
            logger.debug("showProfile is executed!");
        }
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

            return "/profile";
        }
        else{
            model.addAttribute("isAuth", "false");
            return "redirect:/home/index";
        }
    }

    @RequestMapping(value = "/user/update/{id}", method = RequestMethod.GET)
    public String updateUser(Model model, @PathVariable int id) {
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

            return "/user/update";
        }
        else{
            model.addAttribute("isAuth", "false");
            return "/home/index";
        }

    }


    @PostMapping(value = "/user/update/{id}")
    public String updateUser( @Valid User user, BindingResult result,Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByUserName(auth.getName());
        currentUser.setUserName (user.getUserName());
        currentUser.setFirstName(user.getFirstName());
        currentUser.setLastName(user.getLastName());
        currentUser.setEmail(user.getEmail());
        currentUser.setPassword(currentUser.getPassword());
        userService.updateUser(currentUser);
        if(currentUser != null){
            model.addAttribute("loggedUser", currentUser);
            model.addAttribute("isAuth", "true");
            String role = currentUser.getRoles().stream().findFirst().get().getRole().toUpperCase();
            model.addAttribute("role", role);

            List<Recipe> recipes = recipeService.getAllRecipesForLoggedUser(currentUser);
            model.addAttribute("recipes", recipes);
            model.addAttribute("nrOfRecipes", recipes.size());

            List<Rating> ratings = ratingService.getAllRatingsForLoggedUser(currentUser);
            model.addAttribute("nrOfRatings", ratings.size());
        }
        else{
            model.addAttribute("isAuth", "false");
            //return "/home/index";
        }
        if (result.hasErrors()){
            return "/user/update";
        }
        return "/user/update";

    }


}
