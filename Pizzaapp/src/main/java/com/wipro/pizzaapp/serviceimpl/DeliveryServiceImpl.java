package com.wipro.pizzaapp.serviceimpl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.pizzaapp.entity.Delivery;
import com.wipro.pizzaapp.entity.Order;
import com.wipro.pizzaapp.exception.ResourceNotFoundException;
import com.wipro.pizzaapp.repository.DeliveryRepository;
import com.wipro.pizzaapp.repository.OrderRepository;
import com.wipro.pizzaapp.service.DeliveryService;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private OrderRepository orderRepository;

    // ✅ CREATE DELIVERY
    @Override
    public Delivery createDelivery(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (deliveryRepository.existsByOrder_Id(orderId)) {
            return deliveryRepository.findByOrder_Id(orderId);
        }

        Delivery delivery = Delivery.builder()
                .deliveryStatus("PREPARING")
                .etaMinutes(30)
                .createdAt(LocalDateTime.now())
                .order(order)
                .build();

        return deliveryRepository.save(delivery);
    }

    // ✅ GET DELIVERY
    @Override
    public Delivery getDelivery(Long id) {

        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery not found"));

        return updateStatusLogic(delivery);
    }

    // ✅ GET BY ORDER ID
    @Override
    public Delivery getDeliveryByOrderId(Long orderId) {

        Delivery delivery = deliveryRepository.findByOrder_Id(orderId);

        if (delivery == null) {
            throw new ResourceNotFoundException("Delivery not found");
        }

        return updateStatusLogic(delivery);
    }

    // ✅ GET ALL
    @Override
    public List<Delivery> getAllDeliveries() {
        return deliveryRepository.findAll();
    }

    // ✅ ✅ CORE LOGIC (STATUS SYNC 🔥)
    private Delivery updateStatusLogic(Delivery delivery) {

        long minutes = Duration
                .between(delivery.getCreatedAt(), LocalDateTime.now())
                .toMinutes();

        Order order = delivery.getOrder();

        if (minutes >= 2) {
            delivery.setDeliveryStatus("DELIVERED");
            delivery.setEtaMinutes(0);

            order.setStatus("DELIVERED");

        } else if (minutes >= 1) {
            delivery.setDeliveryStatus("OUT_FOR_DELIVERY");
            delivery.setEtaMinutes(10);

            order.setStatus("OUT_FOR_DELIVERY");

        } else {
            delivery.setDeliveryStatus("PREPARING");
            delivery.setEtaMinutes(30);

            order.setStatus("PREPARING");
        }

        // ✅ SAVE BOTH (VERY IMPORTANT)
        orderRepository.save(order);
        return deliveryRepository.save(delivery);
    }

    // ✅ MANUAL UPDATE
    @Override
    public Delivery updateStatus(Long id, String status) {

        Delivery d = deliveryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery not found"));

        d.setDeliveryStatus(status);

        Order order = d.getOrder();
        order.setStatus(status);

        orderRepository.save(order);

        return deliveryRepository.save(d);
    }
}
