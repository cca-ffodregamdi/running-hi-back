package com.runninghi.user.command.application.dto.user.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record FindPasswordRequest(
        @Schema(description = "회원 아이디", example = "qwerty123")
        String account,
        @Schema(description = "회원 이메일", example = "qwe@qwe.qw")
        String email
) {
}