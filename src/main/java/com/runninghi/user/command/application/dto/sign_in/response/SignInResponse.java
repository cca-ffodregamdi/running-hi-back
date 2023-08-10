package com.runninghi.user.command.application.dto.sign_in.response;

import com.runninghi.user.command.domain.aggregate.entity.enumtype.Role;
import io.swagger.v3.oas.annotations.media.Schema;

public record SignInResponse(
        @Schema(description = "회원 이름", example = "qwe")
        String name,
        @Schema(description = "회원 유형", example = "USER")
        Role role,
        String token
) {
}
