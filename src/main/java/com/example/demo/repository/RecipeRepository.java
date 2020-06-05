package com.example.demo.repository;

import com.example.demo.model.Recipe;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    List<Recipe> findByUser(User user);

}
