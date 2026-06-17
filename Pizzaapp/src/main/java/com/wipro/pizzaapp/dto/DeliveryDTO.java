package com.wipro.pizzaapp.dto;

import lombok.Data;

@Data
public class DeliveryDTO {

    private Long orderId;

    private String status;
}
