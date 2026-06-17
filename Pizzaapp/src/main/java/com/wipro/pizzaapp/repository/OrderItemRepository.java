package com.wipro.pizzaapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import com.wipro.pizzaapp.entity.Pizza;
import com.wipro.pizzaapp.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query("SELECT oi.pizza FROM OrderItem oi GROUP BY oi.pizza ORDER BY SUM(oi.quantity) DESC")
    List<Pizza> findTopOrderedPizzas();
}
