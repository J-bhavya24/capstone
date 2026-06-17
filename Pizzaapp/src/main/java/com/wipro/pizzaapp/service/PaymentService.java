package com.wipro.pizzaapp.service;

import com.wipro.pizzaapp.entity.Payment;

public interface PaymentService {

    Payment makePayment(Long orderId, String type);

    Payment getPaymentById(Long paymentId);

    Payment updatePaymentStatus(Long paymentId, String status);
}