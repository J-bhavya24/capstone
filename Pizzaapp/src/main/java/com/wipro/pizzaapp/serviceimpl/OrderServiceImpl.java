package com.wipro.pizzaapp.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.pizzaapp.entity.*;
import com.wipro.pizzaapp.exception.ResourceNotFoundException;
import com.wipro.pizzaapp.repository.*;
import com.wipro.pizzaapp.service.OrderService;
import com.wipro.pizzaapp.service.DeliveryService;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeliveryService deliveryService;

    // ✅ ✅ PLACE ORDER (UPDATED WITH FINAL AMOUNT 🔥)
    @Override
    public Order placeOrder(Long userId, double totalAmount) {

        // ✅ 1. Get user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // ✅ 2. Get cart
        Cart cart = user.getCart();

        if (cart == null || cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // ✅ 3. Convert cart items to order items
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cart.getCartItems()) {
            OrderItem item = OrderItem.builder()
                    .pizza(cartItem.getPizza())
                    .quantity(cartItem.getQuantity())
                    .price(cartItem.getPizza().getPrice())
                    .build();

            orderItems.add(item);
        }

        // ✅ 4. CREATE ORDER USING FINAL AMOUNT (IMPORTANT 🔥)
        Order order = Order.builder()
                .user(user)
                .totalAmount(totalAmount) // ✅ FIXED HERE
                .status("PLACED")
                .orderItems(orderItems)
                .build();

        // ✅ 5. Map order reference
        for (OrderItem item : orderItems) {
            item.setOrder(order);
        }

        // ✅ 6. Save order
        Order savedOrder = orderRepository.save(order);

        // ✅ 7. Create delivery
        deliveryService.createDelivery(savedOrder.getId());

        // ✅ 8. Clear cart
        cart.getCartItems().clear();
        cart.setTotalPrice(0.0);
        cartRepository.save(cart);

        return savedOrder;
    }

    // ✅ GET ORDER BY ID
    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    // ✅ GET ALL ORDERS
    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // ✅ TOTAL REVENUE
    @Override
    public Double getTotalRevenue() {
        Double revenue = orderRepository.getTotalRevenue();
        return revenue != null ? revenue : 0.0;
    }

    // ✅ GET USER ORDERS
    @Override
    public List<Order> getOrdersByUserId(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return orderRepository.findByUser(user);
    }

    // ✅ SAVE ORDER
    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }
}