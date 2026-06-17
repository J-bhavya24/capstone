package com.wipro.pizzaapp.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wipro.pizzaapp.entity.Pizza;
import com.wipro.pizzaapp.repository.PizzaRepository;

@ExtendWith(MockitoExtension.class)
class PizzaServiceImplTest {

    @Mock private PizzaRepository pizzaRepository;

    @InjectMocks private PizzaServiceImpl pizzaService;

    @Test
    void testGetAllPizzas() {
        when(pizzaRepository.findAll()).thenReturn(List.of(new Pizza()));

        List<Pizza> pizzas = pizzaService.getAllPizzas();

        assertFalse(pizzas.isEmpty());
    }
}

