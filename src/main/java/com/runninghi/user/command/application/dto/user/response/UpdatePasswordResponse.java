package com.runninghi.user.command.application.dto.user.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdatePasswordResponse(
        @Schema(description = "비밀번호 수정 성공 여부", example = "true")
        boolean result
) {
    public static UpdatePasswordResponse from(boolean result) {
        return new UpdatePasswordResponse(result);
    }
}
