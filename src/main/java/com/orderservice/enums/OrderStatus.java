package com.orderservice.enums;

public enum OrderStatus {
    PENDING("pending"),
    CREATED("created"),
    FAILED("failed"),
    VALIDATION_ERROR("validation error"),
    PAYMENT_FAILED("payment failed"),
    INVENTORY_FAILED("inventory failed");


    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
    }
