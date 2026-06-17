package com.wipro.pizzaapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.wipro.pizzaapp.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
