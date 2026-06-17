package com.wipro.pizzaapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wipro.pizzaapp.entity.Payment;
import com.wipro.pizzaapp.service.PaymentService;

import com.razorpay.RazorpayClient;
import com.razorpay.Order;
import org.json.JSONObject;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")    
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    // ✅ SAVE PAYMENT (UPDATE ORDER TO PAID)
    @PostMapping("/{orderId}")
    public Payment makePayment(@PathVariable Long orderId,
                              @RequestParam String type) {

        return paymentService.makePayment(orderId, type);
    }


    // ✅ GET PAYMENT
    @GetMapping("/{paymentId}")
    public Payment getPayment(@PathVariable Long paymentId) {
        return paymentService.getPaymentById(paymentId);
    }

    // ✅ RAZORPAY CONFIG
    private static final String KEY_ID = "rzp_test_T1nEpnN9cikzcz";
    private static final String KEY_SECRET = "7s0v4tpETBcjH351jqQRfFSn";

    // ✅ CREATE RAZORPAY ORDER
    @PostMapping("/create-order")
    public String createOrder(@RequestParam int amount) throws Exception {

        RazorpayClient client = new RazorpayClient(KEY_ID, KEY_SECRET);

        JSONObject options = new JSONObject();
        options.put("amount", amount * 100); // ₹ → paisa
        options.put("currency", "INR");
        options.put("receipt", "txn_" + System.currentTimeMillis());

        Order order = client.orders.create(options);

        return order.toString();
    }
}