package com.runninghi.member.command.application.dto.member.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record MemberUpdateRequest(
        @Schema(description = "회원 비밀번호", example = "1234")
        String password,
        @Schema(description = "회원 새 비밀번호", example = "12345")
        String newPassword,
        @Schema(description = "회원 닉네임", example = "qwe")
        String nickname
) {
}
