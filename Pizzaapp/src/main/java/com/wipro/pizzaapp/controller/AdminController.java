package com.wipro.pizzaapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wipro.pizzaapp.entity.Order;
import com.wipro.pizzaapp.repository.OrderRepository;
import com.wipro.pizzaapp.repository.PaymentRepository;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin
public class AdminController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    // ✅ STATS
    @GetMapping("/stats")
    public Map<String, Object> getStats() {

        Map<String, Object> stats = new HashMap<>();

        stats.put("totalOrders", orderRepository.count());

        Double revenue = paymentRepository.getTotalRevenue();
        if (revenue == null) revenue = 0.0;

        stats.put("totalRevenue", revenue);

        return stats;
    }

    // ✅ ALL ORDERS
    @GetMapping("/orders")
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }
}
