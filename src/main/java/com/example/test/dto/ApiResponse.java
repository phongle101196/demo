package com.example.test.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private int status;
    private String message;
    private T data;
    public static <T> ApiResponse<T> success(int status, String message, T data) {
        return new ApiResponse<>(200, message, data);
    }
    public static <T> ApiResponse<T> error(int status, String message) {
        return new ApiResponse<>(status, message, null);
    }
}
