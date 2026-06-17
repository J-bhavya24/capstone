package com.wipro.pizzaapp.dto;

import lombok.Data;

@Data
public class PizzaDTO {

    private String name;
    private String category;
    private String description;
    private Double price;
}
