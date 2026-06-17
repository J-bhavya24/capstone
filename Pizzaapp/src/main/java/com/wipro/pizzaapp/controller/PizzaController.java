package com.wipro.pizzaapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wipro.pizzaapp.entity.Pizza;
import com.wipro.pizzaapp.repository.OrderItemRepository;
import com.wipro.pizzaapp.service.PizzaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pizzas")
@CrossOrigin("*")
public class PizzaController {

    @Autowired
    private PizzaService pizzaService;

    @Autowired
    private OrderItemRepository orderItemRepository;

    // ✅ 1. GET ALL PIZZAS
    @GetMapping
    public List<Pizza> getAllPizzas() {
        return pizzaService.getAllPizzas();
    }

    // ✅ 2. GET PIZZA BY ID
    @GetMapping("/{id}")
    public Pizza getPizza(@PathVariable Long id) {
        return pizzaService.getPizza(id);
    }

    // ✅ 3. ADD NEW PIZZA
    @PostMapping
    public Pizza savePizza(@Valid @RequestBody Pizza pizza) {
        return pizzaService.savePizza(pizza);
    }

    // ✅ 4. UPDATE PIZZA
    @PutMapping("/{id}")
    public Pizza updatePizza(
            @PathVariable Long id,
            @Valid @RequestBody Pizza pizza) {

        return pizzaService.updatePizza(id, pizza);
    }

    // ✅ 5. DELETE PIZZA
    @DeleteMapping("/{id}")
    public void deletePizza(@PathVariable Long id) {
        pizzaService.deletePizza(id);
    }

    // ✅ ✅ 6. VEG PIZZAS (FOR Veg Special)
    @GetMapping("/veg")
    public List<Pizza> getVegPizzas() {
        return pizzaService.getVegPizzas();
    }

    // ✅ ✅ 7. RECENTLY ADDED PIZZAS
    @GetMapping("/recent")
    public List<Pizza> getRecentPizzas() {
        return pizzaService.getRecentPizzas();
    }

    // ✅ ✅ 8. MOST ORDERED PIZZAS
    @GetMapping("/top")
    public List<Pizza> getTopPizzas() {
        return orderItemRepository.findTopOrderedPizzas();
    }
}
