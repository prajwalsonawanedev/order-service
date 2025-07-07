package com.orderservice.serviceImpl;

import com.orderservice.dto.NotificationDto;
import com.orderservice.request.OrderRequestDto;
import com.orderservice.dto.OrderUserDto;
import com.orderservice.entity.Order;
import com.orderservice.enums.OrderStatus;
import com.orderservice.kafka.OrderCreatdProducer;
import com.orderservice.repository.OrderRepository;
import com.orderservice.response.OrderResponseDto;
import com.orderservice.service.OrderService;
import com.orderservice.service.OrderServiceManager;
import com.orderservice.util.GenericMapper;
import com.orderservice.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceManagerImpl implements OrderServiceManager {

    private final JsonUtil jsonUtil;

    private final OrderCreatdProducer orderCreatdProducer;

    private final OrderRepository orderRepository;

    private final GenericMapper genericMapper;

    @Override
    public OrderResponseDto handelFailedOrder(OrderRequestDto orderRequestDto, List<String> errorList) {

        String errorMessage = StringUtils.join(errorList, "- ");

        orderRequestDto.setStatus(OrderStatus.VALIDATION_ERROR.getValue());
        Order order = genericMapper.convert(orderRequestDto, Order.class);
        order = orderRepository.save(order);

        OrderResponseDto orderResponseDto = genericMapper.convert(order, OrderResponseDto.class);
        NotificationDto notificationDto = createNotificationDto(orderResponseDto, errorMessage, false);

        String jsonMessage = jsonUtil.toJson(notificationDto);
        orderCreatdProducer.sendMessageToTopic("notify", jsonMessage);
        return orderResponseDto;
    }

    @Override
    public OrderResponseDto handelSuccessOrder(OrderRequestDto orderRequestDto, List<String> errorList) {

        orderRequestDto.setStatus(OrderStatus.CREATED.getValue());
        orderRequestDto.setTotalAmount(orderRequestDto.getPrice() * orderRequestDto.getQuantity());
        Order order = genericMapper.convert(orderRequestDto, Order.class);
        order = orderRepository.save(order);

        OrderResponseDto orderResponseDto = genericMapper.convert(order, OrderResponseDto.class);
        NotificationDto notificationDto = createNotificationDto(orderResponseDto, null, true);
        String jsonMessage = jsonUtil.toJson(notificationDto);
        orderCreatdProducer.sendMessageToTopic("notify", jsonMessage);
        return orderResponseDto;
    }

    public OrderUserDto createOrderUserDto(OrderRequestDto orderRequestDto, String errorMessage) {
        return OrderUserDto
                .builder()
                .orderId(orderRequestDto.getOrderId())
                .userId(orderRequestDto.getUserId())
                .failReason(errorMessage)
                .build();
    }

    public NotificationDto createNotificationDto(OrderResponseDto orderResponseDto, String errorMessage, Boolean isSuccess) {

        String message = jsonUtil.toJson(orderResponseDto);
        return NotificationDto
                .builder()
                .serviceName("order-service")
                .isSuccess(isSuccess)
                .reasonOfFailure(errorMessage)
                .message(message)
                .build();
    }
}
