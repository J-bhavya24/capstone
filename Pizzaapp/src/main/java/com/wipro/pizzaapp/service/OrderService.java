package com.wipro.pizzaapp.service;

import java.util.List;
import com.wipro.pizzaapp.entity.Order;

public interface OrderService {

	Order placeOrder(Long userId, double totalAmount);

    Order getOrderById(Long orderId);

    List<Order> getAllOrders();

    Double getTotalRevenue();
    
    List<Order> getOrdersByUserId(Long userId);
    
    Order save(Order order);

}