package com.wipro.pizzaapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.wipro.pizzaapp.security.JwtAuthFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .authorizeHttpRequests(auth -> auth

                // ✅ PUBLIC
                .requestMatchers(
                    "/api/auth/**",
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/v3/api-docs/**"
                ).permitAll()

                // ✅ PIZZAS
                .requestMatchers(HttpMethod.GET, "/api/pizzas/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/pizzas/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/pizzas/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/pizzas/**").hasRole("ADMIN")

                // ✅ ✅ COMBOS (FIXED ✅)
                .requestMatchers(HttpMethod.GET, "/api/combos/**")
                .hasAnyRole("CUSTOMER", "ADMIN")

                .requestMatchers(HttpMethod.POST, "/api/combos/**")
                .hasRole("ADMIN")

                .requestMatchers(HttpMethod.PUT, "/api/combos/**")
                .hasRole("ADMIN")

                .requestMatchers(HttpMethod.DELETE, "/api/combos/**")
                .hasRole("ADMIN")

                //  CUSTOMER FEATURES
                .requestMatchers("/api/cart/**").hasAnyRole("CUSTOMER", "ADMIN")
                .requestMatchers("/api/orders/**").hasAnyRole("CUSTOMER", "ADMIN")
                .requestMatchers("/api/payments/**").hasRole("CUSTOMER")
                .requestMatchers("/api/coupons/**").hasAnyRole("CUSTOMER", "ADMIN")

                // ✅ DELIVERY
                .requestMatchers("/api/delivery/**")
                .hasAnyRole("ADMIN", "CUSTOMER", "DELIVERY")

                // ✅ ADMIN
                .requestMatchers("/api/admin/**").hasRole("ADMIN")

                // ✅ EVERYTHING ELSE
                .anyRequest().authenticated()
            )

            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}