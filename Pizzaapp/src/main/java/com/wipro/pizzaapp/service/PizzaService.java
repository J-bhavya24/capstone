package com.wipro.pizzaapp.service;

import java.util.List;
import com.wipro.pizzaapp.entity.Pizza;

public interface PizzaService {

    Pizza savePizza(Pizza pizza);

    Pizza getPizza(Long id);

    List<Pizza> getAllPizzas();

    Pizza updatePizza(Long id, Pizza pizza);

    void deletePizza(Long id);
    
    List<Pizza> getVegPizzas();
    List<Pizza> getRecentPizzas();
   
}