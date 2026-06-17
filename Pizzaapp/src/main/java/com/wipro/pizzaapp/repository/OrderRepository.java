package com.wipro.pizzaapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wipro.pizzaapp.entity.Order;
import com.wipro.pizzaapp.entity.User;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT SUM(o.totalAmount) FROM Order o")
    Double getTotalRevenue();
    
    List<Order> findByUser(User user);
}