package com.orderservice.response;

import com.orderservice.enums.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderResponseDto {

    private Long orderId;

    private String userId;

    private String stockId;

    private Integer quantity;

    private Double price;

    private Double totalAmount;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
