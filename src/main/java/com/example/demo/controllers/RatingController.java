package com.example.demo.controllers;



import com.example.demo.models.Rating;
import com.example.demo.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rating")
public class RatingController {

    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("/addRating")
    public Rating addRating(@RequestBody Rating rating) {
        return ratingService.addRating(rating);
    }

    @PutMapping("/update/{id}")
    public Rating updateRating(@PathVariable String id, @RequestBody Rating updatedRating) {
        return ratingService.updateRating(id, updatedRating);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteRating(@PathVariable String id) {
        ratingService.deleteRating(id);
        return "Deleted rating with ID: " + id;
    }

    @GetMapping("/findByEntity")
    public List<Rating> findRatingsByEntity(@RequestParam Long entityId, @RequestParam String entityType) {
        return ratingService.getRatingsByEntity(entityId, entityType);
    }

    @GetMapping("/findAboveScore")
    public List<Rating> findRatingsAboveScore(@RequestParam int minScore) {
        return ratingService.findRatingsAboveScore(minScore);
    }
}