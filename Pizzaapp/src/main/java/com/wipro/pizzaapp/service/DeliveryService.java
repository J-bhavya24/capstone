package com.wipro.pizzaapp.service;

import java.util.List;
import com.wipro.pizzaapp.entity.Delivery;

public interface DeliveryService {

    Delivery createDelivery(Long orderId);

    Delivery getDelivery(Long id);

    Delivery getDeliveryByOrderId(Long orderId);

    Delivery updateStatus(Long id, String status);

    List<Delivery> getAllDeliveries();
}