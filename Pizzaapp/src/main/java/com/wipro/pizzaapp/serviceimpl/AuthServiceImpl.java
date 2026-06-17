package com.wipro.pizzaapp.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wipro.pizzaapp.dto.AuthResponse;
import com.wipro.pizzaapp.dto.LoginRequest;
import com.wipro.pizzaapp.dto.RegisterRequest;
import com.wipro.pizzaapp.entity.Cart;
import com.wipro.pizzaapp.entity.Role;
import com.wipro.pizzaapp.entity.User;
import com.wipro.pizzaapp.repository.CartRepository;
import com.wipro.pizzaapp.repository.UserRepository;
import com.wipro.pizzaapp.security.JwtService;
import com.wipro.pizzaapp.service.AuthService;

@Service   // ✅ VERY IMPORTANT
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    // ✅ REGISTER
    @Override
    public AuthResponse register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        // Create cart
        Cart cart = new Cart();
        cart.setTotalPrice(0.0);
        cart = cartRepository.save(cart);

        // Create user
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.valueOf(request.getRole().toUpperCase()))
                .cart(cart)
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(
                user.getEmail(),
                user.getRole().name()
        );

        return new AuthResponse(token, user.getRole().name());
    }

    // ✅ LOGIN
    @Override
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(
                user.getEmail(),
                user.getRole().name()
        );

        return new AuthResponse(token, user.getRole().name());
    }
}