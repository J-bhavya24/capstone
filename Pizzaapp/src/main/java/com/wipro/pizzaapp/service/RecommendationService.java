package com.wipro.pizzaapp.service;

import java.util.List;
import com.wipro.pizzaapp.entity.Pizza;

public interface RecommendationService {
    List<Pizza> getRecommendedPizzas();
}