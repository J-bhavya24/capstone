package com.wipro.pizzaapp.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wipro.pizzaapp.repository.OrderRepository;
import com.wipro.pizzaapp.repository.PaymentRepository;
import com.wipro.pizzaapp.serviceimpl.AdminServiceImpl;

@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private AdminServiceImpl adminService;

    @Test
    void testGetStats() {
        when(orderRepository.count()).thenReturn(10L);
        when(paymentRepository.getTotalRevenue()).thenReturn(5000.0);

        Map<String, Object> stats = adminService.getStats();

        assertEquals(10L, stats.get("totalOrders"));
        assertEquals(5000.0, stats.get("totalRevenue"));
    }
}
