package com.orderservice.controller;

import com.orderservice.dto.OrderRequestDto;
import com.orderservice.dto.OrderResponseDto;
import com.orderservice.response.ApiResponse;
import com.orderservice.service.OrderService;
import org.apache.kafka.shaded.com.google.protobuf.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order-service")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/createOrder")
    public ResponseEntity<ApiResponse> createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        ApiResponse apiResponse = orderService.createOrder(orderRequestDto);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
