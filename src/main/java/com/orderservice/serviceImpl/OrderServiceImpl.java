package com.orderservice.serviceImpl;

import com.orderservice.dto.OrderInventoryDto;
import com.orderservice.dto.OrderRequestDto;
import com.orderservice.entity.Order;
import com.orderservice.kafka.OrderCreatdProducer;
import com.orderservice.repository.OrderRepository;
import com.orderservice.response.ApiResponse;
import com.orderservice.service.OrderService;
import com.orderservice.util.GenericMapper;
import com.orderservice.util.JsonUtil;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final GenericMapper genericMapper;

    private final OrderCreatdProducer orderCreatdProducer;

    private final JsonUtil jsonUtil;

    public OrderServiceImpl(OrderRepository orderRepository, GenericMapper genericMapper, OrderCreatdProducer orderCreatdProducer, JsonUtil jsonUtil) {
        this.orderRepository = orderRepository;
        this.genericMapper = genericMapper;
        this.orderCreatdProducer = orderCreatdProducer;
        this.jsonUtil = jsonUtil;
    }

    @Override
    public ApiResponse createOrder(OrderRequestDto orderRequestDto) {

        Order order = genericMapper.convert(orderRequestDto, Order.class);

        Order orderResult = orderRepository.save(order);

        OrderRequestDto orderDto = genericMapper.convert(orderResult, OrderRequestDto.class);

        orderCreatdProducer.sendMessageToTopic(createOrderInventoryDto(orderDto));

        return ApiResponse.response("Your order has been placed successfully and is being processed.", true, orderDto);
    }

    public String createOrderInventoryDto(OrderRequestDto orderRequestDto) {

        OrderInventoryDto orderInventoryDto = new OrderInventoryDto()
                .builder()
                .orderId(orderRequestDto.getOrderId())
                .productName(orderRequestDto.getProductName())
                .quantity(orderRequestDto.getQuantity())
                .price(orderRequestDto.getPrice())
                .build();

        return jsonUtil.toJson(orderInventoryDto);
    }
}
