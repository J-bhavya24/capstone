package com.wipro.pizzaapp.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wipro.pizzaapp.entity.Cart;
import com.wipro.pizzaapp.service.CartService;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin("*")
public class CartController {

    @Autowired
    private CartService cartService;

    // ✅ GET CART
    @GetMapping("/{userId}")
    public Cart getCart(@PathVariable Long userId) {
        return cartService.getCartByUserId(userId);
    }

    // ✅ ADD / UPDATE CART
    @PostMapping
    public Cart addToCart(@RequestBody Map<String, Object> request) {

        Long userId = Long.valueOf(request.get("userId").toString());
        Long pizzaId = Long.valueOf(request.get("pizzaId").toString());
        int quantity = Integer.parseInt(request.get("quantity").toString());

        return cartService.addToCart(userId, pizzaId, quantity);
    }
    
    @DeleteMapping("/{userId}")
    public String clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return "Cart cleared";
    }


    // ✅ ✅ DELETE ITEM (IMPORTANT)
    @DeleteMapping("/item/{itemId}")
    public String removeItem(@PathVariable Long itemId) {
        cartService.removeItem(itemId);
        return "Item removed successfully";
    }
    

}