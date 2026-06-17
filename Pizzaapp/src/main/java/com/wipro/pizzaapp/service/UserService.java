package com.wipro.pizzaapp.service;

import java.util.List;
import com.wipro.pizzaapp.entity.User;

public interface UserService {

    User register(User user);

    User getUser(Long id);

    List<User> getAllUsers();

    User getByEmail(String email);
}