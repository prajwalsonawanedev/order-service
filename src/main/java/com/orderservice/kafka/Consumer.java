package com.orderservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {

    @KafkaListener(topics = "payment-success", groupId = "order-group")
    public void handlePaymentSuccess(String message) throws JsonProcessingException {
        PaymentEvent event = objectMapper.readValue(message, PaymentEvent.class);
        updateOrderStatus(event.getOrderId(), OrderStatus.COMPLETED, "✅ Payment successful");
    }
}
