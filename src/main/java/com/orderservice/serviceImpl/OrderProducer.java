package com.orderservice.serviceImpl;

import com.orderservice.dto.OrderInventoryDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

    private KafkaTemplate<String, Object> kafkaTemplate;

    public OrderProducer(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void orderCreated(OrderInventoryDto orderInventoryDto) {
        kafkaTemplate.send("order-created", orderInventoryDto);
    }
}
