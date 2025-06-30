package com.orderservice.response;

public class ApiResponse<T> {

    private String message;

    private boolean success;

    private T data;

    public ApiResponse() {

    }

    public ApiResponse(String message, boolean success, T data) {
        this.message = message;
        this.success = success;
        this.data = data;
    }

    public static <T> ApiResponse<T> response(String message, boolean success, T data) {
        return new ApiResponse<>(message, success, data);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
