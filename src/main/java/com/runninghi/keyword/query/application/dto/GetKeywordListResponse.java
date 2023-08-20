package com.runninghi.keyword.query.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record GetKeywordListResponse(
        @Schema(description = "키워드 번호")
        Long keywordNo,
        @Schema(description = "키워드 제목")
        String keywordName
) { }
