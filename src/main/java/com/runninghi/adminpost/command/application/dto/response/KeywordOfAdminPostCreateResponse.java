package com.runninghi.adminpost.command.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record KeywordOfAdminPostCreateResponse (
        @Schema(description = "관리자 게시글 번호")
        Long adminPostNo,

        @Schema(description = "키워드 이름")
        String keywordName
)
{
    public static KeywordOfAdminPostCreateResponse of (Long adminPostNo, String keywordName) {
        return new KeywordOfAdminPostCreateResponse(
                adminPostNo, keywordName
        );
    }
}
