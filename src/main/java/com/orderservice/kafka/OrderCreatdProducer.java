package com.orderservice.kafka;

import com.orderservice.dto.OrderInventoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderCreatdProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessageToTopic(String message) {
        System.out.println("inside ordercreated producer");
        kafkaTemplate.send("order-created", message);
    }

}
