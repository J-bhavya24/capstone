package com.wipro.pizzaapp.repository;

import com.wipro.pizzaapp.entity.Combo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComboRepository extends JpaRepository<Combo, Long> {
}