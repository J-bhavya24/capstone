package com.wipro.pizzaapp.dto;

import lombok.Data;

@Data
public class PaymentDTO {

    private Long orderId;

    private Double amount;
}