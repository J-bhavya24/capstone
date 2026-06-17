package com.wipro.pizzaapp.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.pizzaapp.entity.Coupon;
import com.wipro.pizzaapp.repository.CouponRepository;
import com.wipro.pizzaapp.service.CouponService;

@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponRepository couponRepository;

    @Override
    public double applyCoupon(String code, double totalAmount) {

        // ✅ 1. Validate coupon
        Coupon coupon = couponRepository
                .findByCodeAndActiveTrue(code)
                .orElseThrow(() -> new RuntimeException("Invalid or expired coupon"));

        // ✅ 2. Check minimum amount
        if (totalAmount < coupon.getMinAmount()) {
            throw new RuntimeException(
                    "Minimum order amount not met. Required: ₹" + coupon.getMinAmount());
        }

        // ✅ 3. Calculate discount
        double discount = (totalAmount * coupon.getDiscount()) / 100;

        // ✅ 4. Proper rounding
        double finalAmount = Math.round((totalAmount - discount) * 100.0) / 100.0;

        return finalAmount;
    }
}
