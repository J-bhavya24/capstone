package com.wipro.pizzaapp.strategy;

import com.wipro.pizzaapp.entity.Order;
import com.wipro.pizzaapp.entity.Payment;

public interface PaymentStrategy {
    Payment processPayment(Order order);
}
