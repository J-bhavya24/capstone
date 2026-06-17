package com.wipro.pizzaapp.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.pizzaapp.entity.Combo;
import com.wipro.pizzaapp.entity.Pizza;
import com.wipro.pizzaapp.repository.ComboRepository;
import com.wipro.pizzaapp.repository.PizzaRepository;
import com.wipro.pizzaapp.service.ComboService;

@Service
public class ComboServiceImpl implements ComboService {

    @Autowired
    private ComboRepository comboRepository;

    @Autowired
    private PizzaRepository pizzaRepository;

    @Override
    public Combo saveCombo(Combo combo) {

        // ✅ STEP 1: Get Pizza IDs from request
        List<Long> pizzaIds = combo.getPizzas()
                .stream()
                .map(Pizza::getId)
                .collect(Collectors.toList());

        // ✅ STEP 2: Fetch full Pizza objects from DB
        List<Pizza> pizzas = pizzaRepository.findAllById(pizzaIds);

        // ✅ STEP 3: Set pizzas back into combo
        combo.setPizzas(pizzas);

        // ✅ STEP 4: Calculate total price
        double total = pizzas.stream()
                .mapToDouble(Pizza::getPrice)
                .sum();

        // ✅ STEP 5: Apply pricing
        combo.setOriginalPrice(total);
        combo.setDiscountedPrice(total * 0.8); // 20% discount

        // ✅ STEP 6: Save
        return comboRepository.save(combo);
    }

    @Override
    public List<Combo> getAllCombos() {
        return comboRepository.findAll();
    }
}