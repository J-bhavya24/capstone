package com.wipro.pizzaapp.serviceimpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.pizzaapp.entity.Pizza;
import com.wipro.pizzaapp.repository.PizzaRepository;
import com.wipro.pizzaapp.service.PizzaService;

@Service
public class PizzaServiceImpl implements PizzaService {

    @Autowired
    private PizzaRepository pizzaRepository;

    @Override
    public Pizza savePizza(Pizza pizza) {
        return pizzaRepository.save(pizza);
    }

    @Override
    public Pizza getPizza(Long id) {
        return pizzaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Pizza> getAllPizzas() {
        return pizzaRepository.findAll();
    }

    @Override
    public Pizza updatePizza(Long id, Pizza pizza) {
        pizza.setId(id);
        return pizzaRepository.save(pizza);
    }

    @Override
    public void deletePizza(Long id) {
        pizzaRepository.deleteById(id);
    }
    
    @Override
    public List<Pizza> getVegPizzas() {
        return pizzaRepository.findByCategory("VEG");
    }

    @Override
    public List<Pizza> getRecentPizzas() {
        return pizzaRepository.findTop5ByOrderByIdDesc();
    }
}
