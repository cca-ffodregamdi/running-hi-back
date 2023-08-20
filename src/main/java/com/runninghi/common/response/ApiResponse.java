package com.runninghi.common.response;


import com.runninghi.common.response.enumtype.ApiStatus;

public record ApiResponse(
        ApiStatus status,
        String message,
        Object data
) {
    public static ApiResponse success(Object data, String message) {
        return new ApiResponse(ApiStatus.SUCCESS, message, data);
    }

    public static ApiResponse error(String message) {
        return new ApiResponse(ApiStatus.ERROR, message, null);
    }
}
