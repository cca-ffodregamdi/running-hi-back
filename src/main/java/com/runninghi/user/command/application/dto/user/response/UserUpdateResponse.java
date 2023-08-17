package com.runninghi.user.command.application.dto.user.response;

import com.runninghi.user.command.domain.aggregate.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;

public record UserUpdateResponse(
        @Schema(description = "회원 정보 수정 성공 여부", example = "true")
        boolean result,
        @Schema(description = "회원 이름", example = "콜라곰")
        String name
) {
    public static UserUpdateResponse of(boolean result, User user) {
        return new UserUpdateResponse(result, user.getName());
    }
}
