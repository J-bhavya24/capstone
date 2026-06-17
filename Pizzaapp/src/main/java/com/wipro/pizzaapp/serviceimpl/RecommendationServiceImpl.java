package com.wipro.pizzaapp.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.wipro.pizzaapp.entity.Pizza;
import com.wipro.pizzaapp.repository.OrderItemRepository;
import com.wipro.pizzaapp.service.RecommendationService;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public List<Pizza> getRecommendedPizzas() {
        return orderItemRepository.findTopOrderedPizzas();
    }
}