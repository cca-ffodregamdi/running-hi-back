package com.runninghi.user.command.application.dto.sign_up.response;

import com.runninghi.user.command.domain.aggregate.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record SignUpResponse(
        @Schema(description = "회원 고유키", example = "c0a80121-7aeb-4b4b-8b7a-9b9b9b9b9b9b")
        UUID id,
        @Schema(description = "회원 아이디", example = "qwerty123")
        String account,
        @Schema(description = "회원 이름", example = "김철수")
        String name
) {
    public static SignUpResponse from(User user) {
        return new SignUpResponse(
                user.getId(),
                user.getAccount(),
                user.getName()
        );
    }
}
