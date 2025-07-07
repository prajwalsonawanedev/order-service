package com.orderservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class OrderUserDto {

    private Long orderId;

    private String userId;

    private String failReason;
}
