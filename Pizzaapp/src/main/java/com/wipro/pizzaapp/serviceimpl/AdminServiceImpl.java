package com.wipro.pizzaapp.serviceimpl;
 
import java.util.HashMap;

import java.util.Map;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.wipro.pizzaapp.repository.OrderRepository;
import com.wipro.pizzaapp.repository.PaymentRepository;
import com.wipro.pizzaapp.service.AdminService;
 
@Service
public class AdminServiceImpl implements AdminService {
 
    @Autowired
    private OrderRepository orderRepository;
 
    @Autowired
    private PaymentRepository paymentRepository;
 
    @Override
    public Map<String, Object> getStats() {
 
        Map<String, Object> stats = new HashMap<>();
 
        stats.put("totalOrders", orderRepository.count());
 
        Double revenue = paymentRepository.getTotalRevenue();
 
        stats.put("totalRevenue", revenue);
 
        return stats;
    }
}
 