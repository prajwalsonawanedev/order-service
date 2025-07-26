package com.orderservice.kafka;

import com.orderservice.dto.NotificationDto;
import com.orderservice.dto.OrderInventoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderCreatdProducer {


    private final KafkaTemplate<String, NotificationDto> kafkaTemplate;

    public OrderCreatdProducer(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessageToTopic(String topic, NotificationDto message) {
        System.out.println("inside ordercreated producer");
        kafkaTemplate.send(topic, message);
    }

}
