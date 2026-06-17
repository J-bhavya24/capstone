package com.wipro.pizzaapp.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.pizzaapp.entity.Order;
import com.wipro.pizzaapp.entity.Payment;
import com.wipro.pizzaapp.exception.ResourceNotFoundException;
import com.wipro.pizzaapp.repository.OrderRepository;
import com.wipro.pizzaapp.repository.PaymentRepository;
import com.wipro.pizzaapp.service.PaymentService;
import com.wipro.pizzaapp.strategy.RazorpayStrategy;
import com.wipro.pizzaapp.strategy.CashOnDeliveryStrategy;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RazorpayStrategy razorpayStrategy;

    @Autowired
    private CashOnDeliveryStrategy codStrategy;

    @Override
    public Payment makePayment(Long orderId, String type) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        // ✅ prevent duplicate
        if (order.getPayment() != null) {
            return order.getPayment();
        }

        Payment payment;

        // ✅ Strategy logic
        if ("RAZORPAY".equalsIgnoreCase(type)) {
            payment = razorpayStrategy.processPayment(order);
            order.setStatus("PAID");
        } else {
            payment = codStrategy.processPayment(order);
            order.setStatus("PENDING");
        }

        Payment savedPayment = paymentRepository.save(payment);

        order.setPayment(savedPayment);
        orderRepository.save(order);

        return savedPayment;
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));
    }

    @Override
    public Payment updatePaymentStatus(Long id, String status) {

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));

        payment.setPaymentStatus(status);

        return paymentRepository.save(payment);
    }
}