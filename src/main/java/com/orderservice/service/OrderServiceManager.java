package com.orderservice.service;

import com.orderservice.request.OrderRequestDto;
import com.orderservice.response.OrderResponseDto;

import java.util.List;

public interface OrderServiceManager {

    OrderResponseDto handelFailedOrder(OrderRequestDto orderRequestDto, List<String> errorList);

    OrderResponseDto handelSuccessOrder(OrderRequestDto orderRequestDto, List<String> errorList);
}
