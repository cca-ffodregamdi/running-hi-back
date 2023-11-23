package com.runninghi.user.query.application.dto.user.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record FindPasswordResponse(
        @Schema(description = "비밀번호로 계정 조회 결과", example = "true")
        boolean result
) {
    public static FindPasswordResponse from(boolean result) {
        return new FindPasswordResponse(result);
    }
}
