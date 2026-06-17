package com.wipro.pizzaapp.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wipro.pizzaapp.entity.Cart;
import com.wipro.pizzaapp.entity.CartItem;
import com.wipro.pizzaapp.entity.Order;
import com.wipro.pizzaapp.entity.Pizza;
import com.wipro.pizzaapp.entity.User;
import com.wipro.pizzaapp.repository.CartRepository;
import com.wipro.pizzaapp.repository.OrderRepository;
import com.wipro.pizzaapp.repository.UserRepository;
import com.wipro.pizzaapp.service.DeliveryService;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock private OrderRepository orderRepository;
    @Mock private CartRepository cartRepository;
    @Mock private UserRepository userRepository;
    @Mock private DeliveryService deliveryService;

    @InjectMocks private OrderServiceImpl orderService;

    @Test
    void testPlaceOrder() {

        User user = new User();
        user.setId(1L);

        Cart cart = new Cart();

        Pizza pizza = new Pizza();
        pizza.setPrice(100.0);

        CartItem item = new CartItem();
        item.setPizza(pizza);
        item.setQuantity(2);

        // ✅ FIXED (VERY IMPORTANT)
        cart.setCartItems(new ArrayList<>());
        cart.getCartItems().add(item);

        cart.setTotalPrice(200.0);
        user.setCart(cart);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(orderRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        when(deliveryService.createDelivery(any())).thenReturn(null);

        Order order = orderService.placeOrder(1L, 0);

        assertEquals("PLACED", order.getStatus());
    }

}
