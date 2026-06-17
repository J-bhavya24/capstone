package com.wipro.pizzaapp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;        // SAVE10
    private double discount;    // 10 (%)
    private double minAmount;   // minimum cart price

    private boolean active;
}
