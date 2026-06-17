package com.wipro.pizzaapp.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wipro.pizzaapp.entity.Order;
import com.wipro.pizzaapp.entity.Payment;
import com.wipro.pizzaapp.repository.OrderRepository;
import com.wipro.pizzaapp.repository.PaymentRepository;
import com.wipro.pizzaapp.strategy.CashOnDeliveryStrategy;
import com.wipro.pizzaapp.strategy.RazorpayStrategy;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private RazorpayStrategy razorpayStrategy;

    @Mock
    private CashOnDeliveryStrategy codStrategy;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Test
    void testMakePayment_Razorpay() {

        // ✅ Arrange
        Order order = new Order();
        order.setId(1L);
        order.setTotalAmount(500.0);

        Payment mockPayment = new Payment();
        mockPayment.setPaymentStatus("SUCCESS");

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        // ✅ MOCK STRATEGY (VERY IMPORTANT 🔥)
        when(razorpayStrategy.processPayment(order))
                .thenReturn(mockPayment);

        when(paymentRepository.save(any()))
                .thenReturn(mockPayment);

        when(orderRepository.save(any()))
                .thenReturn(order);

        // ✅ Act
        Payment payment = paymentService.makePayment(1L, "RAZORPAY");

        // ✅ Assert
        assertEquals("SUCCESS", payment.getPaymentStatus());
        assertEquals("PAID", order.getStatus());
    }
}