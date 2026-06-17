package com.wipro.pizzaapp.entity;
 
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
 
@Entity
@Table(name = "pizza")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pizza {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    @NotBlank(message = "Pizza name is required")
    private String name;
 
    @NotBlank(message = "Category is required")
    private String category;
 
    @NotBlank(message = "Description is required")
    private String description;
 
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than zero")
    private Double price;
 
    private boolean available;
    

}


 