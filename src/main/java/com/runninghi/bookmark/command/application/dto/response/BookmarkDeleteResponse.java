package com.runninghi.bookmark.command.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record BookmarkDeleteResponse(
        @Schema(description = "즐겨찾기 삭제 성공 여부", example = "true")
        boolean result
) {
}
