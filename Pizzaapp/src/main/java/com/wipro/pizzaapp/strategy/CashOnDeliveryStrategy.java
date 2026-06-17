package com.wipro.pizzaapp.strategy;

import org.springframework.stereotype.Service;

import com.wipro.pizzaapp.entity.Order;
import com.wipro.pizzaapp.entity.Payment;
import com.wipro.pizzaapp.factory.PaymentFactory;

@Service
public class CashOnDeliveryStrategy implements PaymentStrategy {

    @Override
    public Payment processPayment(Order order) {

        return PaymentFactory.createPayment(order, "PENDING");

    }
}
