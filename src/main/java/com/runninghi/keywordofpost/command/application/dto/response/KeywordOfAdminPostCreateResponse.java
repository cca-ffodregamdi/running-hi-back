package com.runninghi.keywordofpost.command.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record KeywordOfAdminPostCreateResponse(
        @Schema(description = "게시글 키워드 번호")
        Long keywordOfPostNo,

        @Schema(description = "관리자 게시글 번호")
        Long adminPostNo,

        @Schema(description = "키워드 번호")
        Long keywordNo
)
{}
