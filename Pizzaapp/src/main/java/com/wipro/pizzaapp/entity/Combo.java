package com.wipro.pizzaapp.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Combo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private double originalPrice;

    private double discountedPrice;

    private String imageUrl;

    // ✅ MANY-TO-MANY RELATIONSHIP
    @ManyToMany
    @JoinTable(
        name = "combo_pizzas",
        joinColumns = @JoinColumn(name = "combo_id"),
        inverseJoinColumns = @JoinColumn(name = "pizza_id")
    )
    private List<Pizza> pizzas;
}
