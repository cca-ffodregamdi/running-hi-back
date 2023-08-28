package com.runninghi.keyword.command.application.dto.request;

import com.runninghi.keyword.command.domain.aggregate.entity.Keyword;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record KeywordUpdateRequest (

    @Schema(description = "작성자 고유 키")
    UUID userKey,

    @Schema(description = "키워드 이름")
    String keywordName
) {
    public static KeywordUpdateRequest from (Keyword keyword) {
        return new KeywordUpdateRequest(
                null,
                keyword.getKeywordName()
        );
    }
}
