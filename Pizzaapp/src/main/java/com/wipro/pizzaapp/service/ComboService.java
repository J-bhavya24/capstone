package com.wipro.pizzaapp.service;

import java.util.List;
import com.wipro.pizzaapp.entity.Combo;

public interface ComboService {

    Combo saveCombo(Combo combo);

    List<Combo> getAllCombos();
}
