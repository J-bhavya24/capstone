package com.wipro.pizzaapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.wipro.pizzaapp.entity.Pizza;
import com.wipro.pizzaapp.service.RecommendationService;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    @GetMapping
    public List<Pizza> getRecommendations() {
        return recommendationService.getRecommendedPizzas();
    }
}