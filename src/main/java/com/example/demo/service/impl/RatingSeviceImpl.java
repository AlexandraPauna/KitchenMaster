package com.example.demo.service.impl;

import com.example.demo.model.Rating;
import com.example.demo.model.Recipe;
import com.example.demo.model.User;
import com.example.demo.repository.RatingRepository;
import com.example.demo.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingSeviceImpl implements RatingService {

    private final RatingRepository ratingRepository;

    @Autowired
    public RatingSeviceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Override
    public Rating saveRating(Rating rating) {
        return ratingRepository.saveAndFlush(rating);
    }

    @Override
    public boolean existsByUserAndRecipe(User user, Recipe recipe) {
        return ratingRepository.existsByUserAndRecipe(user, recipe);
    }

    @Override
    public List<Rating> getAllRatingsForLoggedUser(User user) {
        return ratingRepository.findByUser(user);
    }

    @Override
    public List<Rating> getAllRatingsForRecipe(Recipe recipe) {
        return ratingRepository.findByRecipe(recipe);
    }

    @Override
    public Rating findRatingById(Integer id) {
        Optional<Rating> ratingOptional = ratingRepository.findById(id);

        if (!ratingOptional.isPresent()) {
            throw new RuntimeException("Rating not found!");
        }

        return ratingOptional.get();
    }
}
