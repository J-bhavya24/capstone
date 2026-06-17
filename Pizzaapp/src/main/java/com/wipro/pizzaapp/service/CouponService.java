package com.wipro.pizzaapp.service;

public interface CouponService {

    double applyCoupon(String code, double totalAmount);
}
