package com.wipro.pizzaapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wipro.pizzaapp.entity.Delivery;
import com.wipro.pizzaapp.service.DeliveryService;

@RestController
@RequestMapping("/api/delivery")
@CrossOrigin(origins = "http://localhost:3000")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    @GetMapping
    public List<Delivery> getAllDeliveries() {
        return deliveryService.getAllDeliveries();
    }

    @GetMapping("/{id}")
    public Delivery getDelivery(@PathVariable Long id) {
        return deliveryService.getDelivery(id);
    }

    @GetMapping("/order/{orderId}")
    public Delivery getByOrder(@PathVariable Long orderId) {
        return deliveryService.getDeliveryByOrderId(orderId);
    }

    @PutMapping("/{id}")
    public Delivery updateStatus(@PathVariable Long id,
                                @RequestParam String status) {
        return deliveryService.updateStatus(id, status);
    }
}