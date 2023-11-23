package com.runninghi.user.command.application.dto.user.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record FindAccountRequest(
        @Schema(description = "회원 이름", example = "김철수")
        String name,
        @Schema(description = "회원 이메일", example = "qwe@qwe.qw")
        String email
) {
}
