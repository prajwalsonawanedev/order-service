package com.orderservice.controller;

import com.orderservice.request.OrderRequestDto;
import com.orderservice.response.ApiResponse;
import com.orderservice.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order-service")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create-order")
    public ResponseEntity<ApiResponse> createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        ApiResponse apiResponse = orderService.createOrder(orderRequestDto);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/orders")
    public ResponseEntity<ApiResponse> getAllOrders() {
        ApiResponse apiResponse = orderService.getAllOrders();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/orders-pagination")
    public ResponseEntity<ApiResponse> getAllOrdersByPagination(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        ApiResponse apiResponse = orderService.getAllOrdersByPagination(pageNumber, pageSize);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
