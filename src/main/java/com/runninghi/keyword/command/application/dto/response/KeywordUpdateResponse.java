package com.runninghi.keyword.command.application.dto.response;

import com.runninghi.keyword.command.domain.aggregate.entity.Keyword;
import io.swagger.v3.oas.annotations.media.Schema;

public record KeywordUpdateResponse (
        @Schema(description = "키워드 번호")
        Long keywordNo,

        @Schema(description = "키워드 이름")
        String keywordName
)
{
    public static KeywordUpdateResponse of(Keyword keyword) {
        return new KeywordUpdateResponse(
                keyword.getKeywordNo(),
                keyword.getKeywordName()
        );
    }
}
