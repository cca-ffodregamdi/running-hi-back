package com.runninghi.keyword.query.application.dto.response;

import com.runninghi.keyword.command.domain.aggregate.entity.Keyword;
import io.swagger.v3.oas.annotations.media.Schema;

public record FindKeywordResponse(
        @Schema(description = "키워드 번호")
        Long keywordNo,
        @Schema(description = "키워드 제목")
        String keywordName
) {
        public static FindKeywordResponse of(Keyword keyword) {
                return new FindKeywordResponse(
                        keyword.getKeywordNo(),
                        keyword.getKeywordName()
                );
        }
}
