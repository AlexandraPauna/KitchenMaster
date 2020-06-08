package com.example.demo.repository;

import com.example.demo.model.Rating;
import com.example.demo.model.Recipe;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
    boolean existsByUserAndRecipe(User user, Recipe recipe);
    List<Rating> findByUser(User user);
    List<Rating> findByRecipe(Recipe recipe);
}
