package com.wipro.pizzaapp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.wipro.pizzaapp.entity.Pizza;

public interface PizzaRepository extends JpaRepository<Pizza, Long> {

    List<Pizza> findByCategory(String category);

    // ✅ ✅ ADD THIS METHOD (IMPORTANT)
    List<Pizza> findTop5ByOrderByIdDesc();
}
