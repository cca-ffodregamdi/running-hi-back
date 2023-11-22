package com.runninghi.user.command.application.dto.sign_up.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record VerifyDuplicationIdRequest(
        @Schema(description = "회원 아이디", example = "qwerty")
        String account
) {

}
