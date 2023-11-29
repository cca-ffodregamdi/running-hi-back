package com.runninghi.keyword.command.application.dto.response;

import com.runninghi.member.command.domain.aggregate.entity.enumtype.Role;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserCheckResponse(
        @Schema(description = "회원 고유키", example = "c0a80121-7aeb-4b4b-8b0a-6b1c032f0e4a")
        UUID id,
        @Schema(description = "회원 아이디", example = "qwerty123")
        String account,
        @Schema(description = "회원 이름", example = "qwe")
        String name,
        @Schema(description = "회원 타입", example = "USER")
        Role role,
        @Schema(description = "회원 생성일", example = "2023-05-11T15:00:00")
        LocalDateTime createdAt
) {
}
