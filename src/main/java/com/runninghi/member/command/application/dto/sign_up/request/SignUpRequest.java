package com.runninghi.member.command.application.dto.sign_up.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record SignUpRequest(
        @Schema(description = "회원 아이디", example = "qwerty123")
        String account,
        @Schema(description = "회원 비밀번호", example = "1234")
        String password,
        @Schema(description = "회원 이름", example = "김철수")
        String name,
        @Schema(description = "회원 이메일", example = "qwe@qwe.qw")
        String email,
        @Schema(description = "회원 닉네임", example = "qwe")
        String nickname
) {
}
