package com.wipro.pizzaapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.wipro.pizzaapp.dto.CouponApplyRequest;
import com.wipro.pizzaapp.dto.CouponResponse;
import com.wipro.pizzaapp.entity.Cart;
import com.wipro.pizzaapp.service.CartService;
import com.wipro.pizzaapp.service.CouponService;

@RestController
@RequestMapping("/api/coupons")
@CrossOrigin("*")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @Autowired
    private CartService cartService;

    // ✅ REAL APPLY COUPON API
    @PostMapping("/apply")
    public ResponseEntity<CouponResponse> applyCoupon(
            @RequestBody CouponApplyRequest request) {

        try {
            // ✅ Step 1: get user cart
            Cart cart = cartService.getCartByUserId(request.getUserId());

            if (cart == null || cart.getCartItems().isEmpty()) {

                return ResponseEntity.badRequest().body(
                        CouponResponse.builder()
                                .status("FAILED")
                                .message("Cart is empty")
                                .build()
                );
            }

            double originalAmount = cart.getTotalPrice();

            // ✅ Step 2: apply coupon
            double finalAmount = couponService
                    .applyCoupon(request.getCode(), originalAmount);

            double discount = originalAmount - finalAmount;

            // ✅ Step 3: success response
            CouponResponse response = CouponResponse.builder()
                    .status("SUCCESS")
                    .message("Coupon applied successfully")
                    .couponCode(request.getCode())
                    .originalAmount(originalAmount)
                    .discountApplied(discount)
                    .finalAmount(finalAmount)
                    .build();

            return ResponseEntity.ok(response);

        } catch (Exception e) {

            // ✅ Step 4: failure response
            CouponResponse errorResponse = CouponResponse.builder()
                    .status("FAILED")
                    .message(e.getMessage())
                    .build();

            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    // ✅ GET AVAILABLE COUPONS (for UI display)
    @GetMapping
    public ResponseEntity<?> getCoupons() {

        return ResponseEntity.ok().body(
                new String[]{
                        "SAVE10 - 10% off above ₹200",
                        "SAVE20 - 20% off above ₹500",
                        "FIRST50 - 50% off above ₹1000"
                }
        );
    }
}