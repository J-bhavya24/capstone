package com.wipro.pizzaapp.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CouponResponse {

    private String status;
    private String message;

    private String couponCode;

    private double originalAmount;
    private double discountApplied;
    private double finalAmount;
}

