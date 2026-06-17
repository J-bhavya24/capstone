package com.wipro.pizzaapp.repository;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
 
import com.wipro.pizzaapp.entity.Payment;
 
public interface PaymentRepository extends JpaRepository<Payment, Long> {
 
    Payment findByOrder_Id(Long orderId);
 
    @Query("SELECT COALESCE(SUM(p.amount),0) FROM Payment p WHERE p.paymentStatus='SUCCESS'")
    Double getTotalRevenue();
}