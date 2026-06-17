package com.wipro.pizzaapp.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wipro.pizzaapp.entity.Combo;
import com.wipro.pizzaapp.entity.Pizza;
import com.wipro.pizzaapp.repository.ComboRepository;
import com.wipro.pizzaapp.repository.PizzaRepository;

@ExtendWith(MockitoExtension.class)
	class ComboServiceImplTest {

	    @Mock private ComboRepository comboRepository;
	    @Mock private PizzaRepository pizzaRepository;

	    @InjectMocks private ComboServiceImpl comboService;

	    @Test
	    void testSaveCombo() {

	        Pizza p = new Pizza();
	        p.setId(1L);
	        p.setPrice(100.0);

	        Combo combo = new Combo();
	        combo.setPizzas(List.of(p));

	        when(pizzaRepository.findAllById(any())).thenReturn(List.of(p));
	        when(comboRepository.save(any())).thenAnswer(i -> i.getArgument(0));

	        Combo saved = comboService.saveCombo(combo);

	        assertEquals(80, saved.getDiscountedPrice());
	    }
	}