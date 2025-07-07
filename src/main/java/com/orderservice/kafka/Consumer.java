package com.orderservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.orderservice.dto.NotificationDto;
import com.orderservice.request.OrderRequestDto;
import com.orderservice.service.OrderService;
import com.orderservice.service.OrderServiceManager;
import com.orderservice.util.JsonUtil;
import com.orderservice.validation.OrderValidation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Consumer {

    private static final Logger log = LoggerFactory.getLogger(Consumer.class);

    private final JsonUtil jsonUtil;

    private final OrderService orderService;

    private final OrderServiceManager orderServiceManager;

//    @KafkaListener(topics = "payment-success", groupId = "order-group")
//    public void handlePaymentSuccess(String message) throws JsonProcessingException {
//        PaymentEvent event = objectMapper.readValue(message, PaymentEvent.class);
//        updateOrderStatus(event.getOrderId(), OrderStatus.COMPLETED, "✅ Payment successful");
//    }

    @KafkaListener(topics = "user-order-created", groupId = "user-order-group")
    public void readMessageUserOrderCreated(String message) throws JsonProcessingException {

        log.info("Message received: {}", message);

        OrderRequestDto OrderRequestDto = jsonUtil.fromJson(message, OrderRequestDto.class);

        List<String> errorList = OrderValidation.validateOrder(OrderRequestDto);

        if (!CollectionUtils.isEmpty(errorList)) {
            orderServiceManager.handelFailedOrder(OrderRequestDto, errorList);
            return;
        }
        orderServiceManager.handelSuccessOrder(OrderRequestDto, errorList);
    }

    @KafkaListener(topics = "notify", groupId = "")
    public void readMessageFromNotification(String message) throws JsonProcessingException {
        log.info("Message received: {}", message);

        NotificationDto notificationDto = jsonUtil.fromJson(message, NotificationDto.class);
    }

}
