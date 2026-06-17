package com.wipro.pizzaapp.serviceimpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;

import com.wipro.pizzaapp.dto.*;
import com.wipro.pizzaapp.entity.*;
import com.wipro.pizzaapp.repository.*;
import com.wipro.pizzaapp.security.JwtService;

import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock private UserRepository userRepository;
    @Mock private CartRepository cartRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private JwtService jwtService;

    @InjectMocks private AuthServiceImpl authService;

    @Test
    void testRegister() {

        RegisterRequest req = new RegisterRequest();
        req.setName("Bhavya");
        req.setEmail("test@mail.com");
        req.setPassword("1234");
        req.setRole("CUSTOMER");

        when(userRepository.findByEmail(req.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode("1234")).thenReturn("encoded");
        when(jwtService.generateToken(any(), any())).thenReturn("token");

        AuthResponse response = authService.register(req);

        assertEquals("token", response.getToken());
    }

    @Test
    void testLogin() {

        User user = User.builder()
                .email("test@mail.com")
                .password("encoded")
                .role(Role.CUSTOMER)
                .build();

        LoginRequest req = new LoginRequest();
        req.setEmail("test@mail.com");
        req.setPassword("1234");

        when(userRepository.findByEmail(req.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("1234", "encoded")).thenReturn(true);
        when(jwtService.generateToken(any(), any())).thenReturn("token");

        AuthResponse res = authService.login(req);

        assertEquals("token", res.getToken());
    }
}
