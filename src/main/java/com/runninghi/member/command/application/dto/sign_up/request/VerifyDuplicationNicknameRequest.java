package com.runninghi.member.command.application.dto.sign_up.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record VerifyDuplicationNicknameRequest(
        @Schema(description = "회원 닉네임", example = "qwe")
        String nickname
) {

}
