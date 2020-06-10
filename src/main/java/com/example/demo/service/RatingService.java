package com.example.demo.service;

import com.example.demo.model.Rating;
import com.example.demo.model.Recipe;
import com.example.demo.model.User;

import java.util.List;

public interface RatingService {
    Rating saveRating(Rating rating);
    boolean existsByUserAndRecipe(User user, Recipe recipe);
    List<Rating> getAllRatingsForLoggedUser(User user);
    List<Rating> getAllRatingsForRecipe(Recipe recipe);
    Rating findRatingById(Integer id);
}
