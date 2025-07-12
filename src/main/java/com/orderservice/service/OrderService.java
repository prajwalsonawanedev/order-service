package com.orderservice.service;

import com.orderservice.request.OrderRequestDto;
import com.orderservice.dto.OrderUserDto;
import com.orderservice.response.ApiResponse;

public interface OrderService {

    ApiResponse createOrder(OrderRequestDto orderRequestDto);

    void rejectOrderDueToValidation(OrderUserDto orderUserDto);

    OrderRequestDto saveOrder(OrderRequestDto orderRequestDto);

    ApiResponse getAllOrders();

    ApiResponse getAllOrdersByPagination(Integer pageNumber, Integer pageSize);
}
