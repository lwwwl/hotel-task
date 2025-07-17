package com.example.hoteltask.model.response;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private long timestamp;
    private int statusCode;
    private String message;
    private T data;
    private String error;

    public static <T> ApiResponse<T> success(T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setTimestamp(System.currentTimeMillis());
        response.setStatusCode(200);
        response.setMessage("success");
        response.setData(data);
        response.setError(null);
        return response;
    }

    public static <T> ApiResponse<T> error(int statusCode, String message, String error) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setTimestamp(System.currentTimeMillis());
        response.setStatusCode(statusCode);
        response.setMessage(message);
        response.setData(null);
        response.setError(error);
        return response;
    }
} 