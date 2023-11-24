package com.runninghi.member.command.application.dto.member.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdatePasswordRequest(
        @Schema(description = "회원 아이디", example = "qwerty123")
        String account,
        @Schema(description = "회원 새 비밀번호", example = "12345")
        String password
) {
}
