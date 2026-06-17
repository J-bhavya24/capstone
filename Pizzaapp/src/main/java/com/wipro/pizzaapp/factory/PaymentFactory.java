package com.wipro.pizzaapp.factory;

import com.wipro.pizzaapp.entity.Order;
import com.wipro.pizzaapp.entity.Payment;

public class PaymentFactory {

    public static Payment createPayment(Order order, String status) {

        return Payment.builder()
                .amount(order.getTotalAmount())
                .paymentStatus(status)
                .order(order)
                .build();
    }
}