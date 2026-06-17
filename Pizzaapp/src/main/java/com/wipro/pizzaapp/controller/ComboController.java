package com.wipro.pizzaapp.controller;

import com.wipro.pizzaapp.entity.Combo;
import com.wipro.pizzaapp.service.ComboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/combos")
@CrossOrigin(origins = "*")
public class ComboController {

    @Autowired
    private ComboService comboService;

    @GetMapping
    public List<Combo> getAllCombos() {
        return comboService.getAllCombos();
    }

    @PostMapping
    public Combo createCombo(@RequestBody Combo combo) {
        return comboService.saveCombo(combo);
    }
}
