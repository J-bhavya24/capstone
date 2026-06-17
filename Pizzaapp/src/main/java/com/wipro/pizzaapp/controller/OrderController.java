package com.wipro.pizzaapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wipro.pizzaapp.entity.Order;
import com.wipro.pizzaapp.service.OrderService;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin("*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // GET ALL ORDERS (admin)
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    //  GET ORDER BY ID
    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @PostMapping("/place/{userId}")
    public Order placeOrder(@PathVariable Long userId,
                            @RequestBody java.util.Map<String, Double> request) {

        double finalAmount = request.get("totalAmount");

        return orderService.placeOrder(userId, finalAmount);
    }

    // GET USER ORDERS
    @GetMapping("/my/{userId}")
    public List<Order> getMyOrders(@PathVariable Long userId) {
        return orderService.getOrdersByUserId(userId); 
        
    }
    
    @PutMapping("/status/{orderId}/{status}")
    public Order updateStatus(@PathVariable Long orderId, @PathVariable String status) {
        Order order = orderService.getOrderById(orderId);
        order.setStatus(status);
        return orderService.save(order);
    }
    
    
}
