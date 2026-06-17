package com.wipro.pizzaapp.service;

import com.wipro.pizzaapp.entity.Cart;

public interface CartService {

    // ✅ Add item to cart
    Cart addToCart(Long userId, Long pizzaId, int quantity);

    // ✅ Get cart by userId
    Cart getCartByUserId(Long userId);

    // ✅ Clear cart (optional but useful after order)
    Cart clearCart(Long userId);
    
    void removeItem(Long itemId);
}