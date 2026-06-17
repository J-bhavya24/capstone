package com.wipro.pizzaapp.service;

import com.wipro.pizzaapp.dto.AuthResponse;
import com.wipro.pizzaapp.dto.LoginRequest;
import com.wipro.pizzaapp.dto.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}