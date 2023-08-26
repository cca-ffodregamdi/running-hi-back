package com.runninghi.adminpost.command.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record KeywordListRequest(
        @Schema(description = "키워드 번호")
        Long keywordNo,

        @Schema(description = "키워드 이름")
        String keywordName
) {
    public static KeywordListRequest of (Long keywordNo, String keywordName) {
        return new KeywordListRequest(keywordNo, keywordName);
    }
}
