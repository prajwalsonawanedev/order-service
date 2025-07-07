package com.orderservice.serviceImpl;

import com.orderservice.dto.OrderInventoryDto;
import com.orderservice.request.OrderRequestDto;
import com.orderservice.dto.OrderUserDto;
import com.orderservice.entity.Order;
import com.orderservice.kafka.OrderCreatdProducer;
import com.orderservice.repository.OrderRepository;
import com.orderservice.response.ApiResponse;
import com.orderservice.response.OrderResponseDto;
import com.orderservice.service.OrderService;
import com.orderservice.service.OrderServiceManager;
import com.orderservice.util.GenericMapper;
import com.orderservice.util.JsonUtil;
import com.orderservice.validation.OrderValidation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final GenericMapper genericMapper;

    private final OrderCreatdProducer orderCreatdProducer;

    private final OrderServiceManager orderServiceManager;

    private final JsonUtil jsonUtil;

    private List<String> errorList = new ArrayList<>();

//    public OrderServiceImpl(OrderRepository orderRepository, GenericMapper genericMapper, OrderCreatdProducer orderCreatdProducer, JsonUtil jsonUtil) {
//        this.orderRepository = orderRepository;
//        this.genericMapper = genericMapper;
//        this.orderCreatdProducer = orderCreatdProducer;
//        this.jsonUtil = jsonUtil;
//    }

    @Override
    public ApiResponse createOrder(OrderRequestDto orderRequestDto) {

        if (validateOrder(orderRequestDto)) {
            OrderResponseDto orderResponseDto = orderServiceManager.handelSuccessOrder(orderRequestDto, errorList);
            return ApiResponse.response("Successfully Created Order", true, "Success", orderResponseDto);
        }

        String errorMessage = StringUtils.join(errorList, "- ");
        OrderResponseDto orderResponseDto = orderServiceManager.handelFailedOrder(orderRequestDto, errorList);
        return ApiResponse.response("Validation Error", false, errorMessage, orderResponseDto);
    }

    public OrderRequestDto saveOrder(OrderRequestDto orderRequestDto) {

        Order order = genericMapper.convert(orderRequestDto, Order.class);

        Order orderResult = orderRepository.save(order);

        OrderRequestDto orderDto = genericMapper.convert(orderResult, OrderRequestDto.class);

        return orderDto;
    }

    public String createOrderInventoryDto(OrderRequestDto orderRequestDto) {

        OrderInventoryDto orderInventoryDto = new OrderInventoryDto()
                .builder()
                .orderId(orderRequestDto.getOrderId())
                //.productName(orderRequestDto.getProductName())
                .quantity(orderRequestDto.getQuantity())
                .price(orderRequestDto.getPrice())
                .build();

        return jsonUtil.toJson(orderInventoryDto);
    }

    public void rejectOrderDueToValidation(OrderUserDto orderUserDto) {
        String message = jsonUtil.toJson(orderUserDto);

        orderCreatdProducer.sendMessageToTopic("order-user-rejected", message);

    }


    public ApiResponse getAllOrders() {

        List<OrderResponseDto> orderList = orderRepository.findAll()
                .stream()
                .map(order -> genericMapper.convert(order, OrderResponseDto.class))
                .toList();

        return ApiResponse.response("Order Details Found", true, null, orderList);
    }


    public ApiResponse getAllOrdersByPagination(Integer pageNumber, Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<Order> pageOrder = orderRepository.findAll(pageable);

        List<OrderResponseDto> orderList = pageOrder.getContent()
                .stream()
                .map(order -> genericMapper.convert(order, OrderResponseDto.class))
                .toList();

        return ApiResponse.response("Order Details Found", true, null, orderList);
    }

    public boolean validateOrder(OrderRequestDto orderRequestDto) {

        errorList = OrderValidation.validateOrder(orderRequestDto);

        if (!CollectionUtils.isEmpty(errorList)) {
            return false;
        }
        return true;
    }
}
