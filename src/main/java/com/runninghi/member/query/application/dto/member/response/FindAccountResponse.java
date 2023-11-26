package com.runninghi.member.query.application.dto.member.response;

import com.runninghi.member.command.domain.aggregate.Member;
import io.swagger.v3.oas.annotations.media.Schema;

public record FindAccountResponse(
        @Schema(description = "회원 아이디", example = "qwerty123")
        String account
) {
    public static FindAccountResponse from(Member member) {
        return new FindAccountResponse(
                member.getAccount()
        );
    }
}
