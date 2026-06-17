package com.wipro.pizzaapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.wipro.pizzaapp.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}