package com.orderservice.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDto {

    private String serviceName;

    private String reasonOfFailure;

    private Boolean isSuccess;

    //  private String message;

    private MasterDto masterDto;

}
