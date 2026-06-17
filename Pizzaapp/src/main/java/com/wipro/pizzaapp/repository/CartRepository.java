package com.wipro.pizzaapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.wipro.pizzaapp.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
}