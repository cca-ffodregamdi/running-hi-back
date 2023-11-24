package com.runninghi.member.command.application.dto.sign_in.response;

import com.runninghi.member.command.domain.aggregate.entity.enumtype.Role;
import io.swagger.v3.oas.annotations.media.Schema;

public record SignInResponse(
        @Schema(description = "회원 이름", example = "김철수")
        String name,
        @Schema(description = "회원 닉네임", example = "qwe")
        String nickname,
        @Schema(description = "회원 이메일", example = "qwe@qwe.qw")
        String email,
        @Schema(description = "회원 유형", example = "USER")
        Role role,
        @Schema(description = "Access Token", example = "qweweqwqewqe")
        String accessToken,
        @Schema(description = "Refresh Token", example = "adsdasadssad")
        String refreshToken
) {
}
