package com.orderservice.service;

import com.orderservice.dto.OrderRequestDto;
import com.orderservice.dto.OrderResponseDto;
import com.orderservice.response.ApiResponse;

public interface OrderService {

    ApiResponse createOrder(OrderRequestDto orderRequestDto);
}
