package com.wipro.pizzaapp.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String deliveryStatus;
    private int etaMinutes;
    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name = "order_id")
    @JsonIgnoreProperties({"delivery", "payment", "orderItems", "user"})
    private Order order;

    // ✅ FIX: send orderId to frontend
    @JsonProperty("orderId")
    public Long getOrderId() {
        return order != null ? order.getId() : null;
    }
}