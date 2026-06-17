package com.wipro.pizzaapp.entity;
 
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
 
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    @NotBlank(message = "Name is required")
    private String name;
 
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    @Column(unique = true)
    private String email;
 
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    @JsonIgnore
    private String password;
 
    @Enumerated(EnumType.STRING)
    private Role role;
 
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    @JsonIgnore  
    private Cart cart;
 
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore 
    private List<Order> orders;
}
 