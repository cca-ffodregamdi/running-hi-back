package com.runninghi.member.command.application.dto.sign_up.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record VerifyDuplicationNicknameResponse(
        @Schema(description = "중복 확인 결과", example = "false")
        boolean result
) {
    
}
