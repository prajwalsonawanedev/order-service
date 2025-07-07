package com.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class NotificationDto {

    private String serviceName;

    private String reasonOfFailure;

    private Boolean isSuccess;

    private String message;

}
