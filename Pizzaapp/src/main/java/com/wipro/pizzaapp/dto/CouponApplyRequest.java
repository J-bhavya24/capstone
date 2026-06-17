package com.wipro.pizzaapp.dto;

import lombok.Data;

@Data
public class CouponApplyRequest {

    private Long userId;
    private String code;
}