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

import com.wipro.pizzaapp.entity.Cart;
import com.wipro.pizzaapp.entity.Pizza;
import com.wipro.pizzaapp.entity.User;
import com.wipro.pizzaapp.repository.CartItemRepository;
import com.wipro.pizzaapp.repository.CartRepository;
import com.wipro.pizzaapp.repository.PizzaRepository;
import com.wipro.pizzaapp.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
	class CartServiceImplTest {

	    @Mock private UserRepository userRepository;
	    @Mock private PizzaRepository pizzaRepository;
	    @Mock private CartRepository cartRepository;
	    @Mock private CartItemRepository cartItemRepository;

	    @InjectMocks private CartServiceImpl cartService;

	    @Test
	    void testAddToCart() {

	        User user = new User();
	        user.setId(1L);
	        user.setCart(new Cart());

	        Pizza pizza = new Pizza();
	        pizza.setId(1L);
	        pizza.setPrice(200.0);

	        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
	        when(pizzaRepository.findById(1L)).thenReturn(Optional.of(pizza));
	        when(cartRepository.save(any())).thenAnswer(i -> i.getArgument(0));

	        Cart cart = cartService.addToCart(1L, 1L, 2);

	        assertEquals(400.0, cart.getTotalPrice());
	    }
	}


