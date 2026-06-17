package com.wipro.pizzaapp.serviceimpl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.pizzaapp.entity.*;
import com.wipro.pizzaapp.repository.*;
import com.wipro.pizzaapp.service.CartService;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    // ✅ ADD TO CART
    @Override
    public Cart addToCart(Long userId, Long pizzaId, int quantity) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Pizza pizza = pizzaRepository.findById(pizzaId)
                .orElseThrow(() -> new RuntimeException("Pizza not found"));

        Cart cart = user.getCart();

        if (cart == null) {
            cart = new Cart();
            cart.setCartItems(new ArrayList<>());
            cart.setTotalPrice(0.0);
        }

        if (cart.getCartItems() == null) {
            cart.setCartItems(new ArrayList<>());
        }

        boolean found = false;

        for (CartItem item : cart.getCartItems()) {

            if (item.getPizza().getId().equals(pizzaId)) {

                if (quantity <= 0) {
                    cart.getCartItems().remove(item);
                } else {
                    item.setQuantity(quantity);
                }

                found = true;
                break;
            }
        }

        if (!found && quantity > 0) {
            CartItem newItem = CartItem.builder()
                    .cart(cart)
                    .pizza(pizza)
                    .quantity(quantity)
                    .build();

            cart.getCartItems().add(newItem);
        }

        // ✅ recalculate total
        double total = 0;
        for (CartItem item : cart.getCartItems()) {
            total += item.getPizza().getPrice() * item.getQuantity();
        }

        cart.setTotalPrice(total);
        user.setCart(cart);

        return cartRepository.save(cart);
    }

    // ✅ GET CART
    @Override
    public Cart getCartByUserId(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = user.getCart();

        if (cart == null) {
            cart = new Cart();
            cart.setCartItems(new ArrayList<>());
            cart.setTotalPrice(0.0);
        }

        return cart;
    }

    // ✅ CLEAR CART
    @Override
    public Cart clearCart(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = user.getCart();

        if (cart != null) {
            cart.getCartItems().clear();
            cart.setTotalPrice(0.0);
        }

        return cartRepository.save(cart);
    }

    // ✅ ✅ FINAL REMOVE ITEM (FIXED ✅)
    @Override
    public void removeItem(Long itemId) {

        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        Cart cart = item.getCart();

        // ✅ SAFE REMOVE
        cart.getCartItems().removeIf(i -> i.getId().equals(itemId));

        // ✅ DELETE FROM DB
        cartItemRepository.deleteById(itemId);

        // ✅ RECALCULATE TOTAL
        double total = cart.getCartItems().stream()
                .mapToDouble(i -> i.getPizza().getPrice() * i.getQuantity())
                .sum();

        cart.setTotalPrice(total);

        cartRepository.save(cart);
    }
}
