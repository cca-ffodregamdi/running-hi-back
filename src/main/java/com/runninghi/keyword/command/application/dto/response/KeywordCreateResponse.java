package com.runninghi.keyword.command.application.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;

public record KeywordCreateResponse(
        @Schema(description = "키워드 번호")
        Long keywordNo,
        @Schema(description = "키워드 제목")
        String keywordName
) {

        public static KeywordCreateResponse of (Long keywordNo, String keywordName) {
                return new KeywordCreateResponse( keywordNo, keywordName);
        }
}
