package com.runninghi.keyword.command.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record KeywordCreateRequest(
        @Schema(description = "작성자 고유 키")
        UUID userKey,

        @Schema(description = "키워드 이름")
        String keywordName
) {
}
