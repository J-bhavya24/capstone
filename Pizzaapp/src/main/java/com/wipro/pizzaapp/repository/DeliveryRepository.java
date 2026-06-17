package com.wipro.pizzaapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.wipro.pizzaapp.entity.Delivery;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    // ✅ FIXED (IMPORTANT)
    boolean existsByOrder_Id(Long orderId);

    Delivery findByOrder_Id(Long orderId);
}