package com.runninghi.user.query.application.dto.user.response;

import com.runninghi.user.command.domain.aggregate.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;

public record FindAccountResponse(
        @Schema(description = "회원 아이디", example = "qwerty123")
        String account
) {
    public static FindAccountResponse from(User user) {
        return new FindAccountResponse(
                user.getAccount()
        );
    }
}
