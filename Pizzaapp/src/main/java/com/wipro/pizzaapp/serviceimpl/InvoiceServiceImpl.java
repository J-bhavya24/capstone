package com.wipro.pizzaapp.serviceimpl;

import java.io.ByteArrayOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import com.wipro.pizzaapp.entity.Order;
import com.wipro.pizzaapp.entity.OrderItem;
import com.wipro.pizzaapp.repository.OrderRepository;
import com.wipro.pizzaapp.service.InvoiceService;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public byte[] generateInvoice(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // ✅ TITLE
            document.add(new Paragraph("🍕 Pizza App Invoice"));
            document.add(new Paragraph("Order ID: " + order.getId()));
            document.add(new Paragraph("Status: " + order.getStatus()));
            document.add(new Paragraph(" "));

            // ✅ ITEMS
            document.add(new Paragraph("Items:"));

            for (OrderItem item : order.getOrderItems()) {
                document.add(new Paragraph(
                        item.getPizza().getName() +
                        " x " + item.getQuantity() +
                        " = ₹" + (item.getPrice() * item.getQuantity())
                ));
            }

            document.add(new Paragraph(" "));
            document.add(new Paragraph("Total Amount: ₹" + order.getTotalAmount()));

            document.close();

            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Error generating invoice");
        }
    }
}
