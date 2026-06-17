package com.wipro.pizzaapp.service;

public interface InvoiceService {
    byte[] generateInvoice(Long orderId);
}