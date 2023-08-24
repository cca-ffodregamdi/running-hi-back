package com.runninghi.bookmarkfolder.command.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record FolderDeleteResponse(
        @Schema(description = "폴더 삭제 성공 여부", example = "true")
        boolean result
) {
}
