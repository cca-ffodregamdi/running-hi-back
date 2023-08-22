package com.runninghi.bookmark.query.application.dto;

import com.runninghi.bookmark.command.domain.aggregate.vo.BookmarkVO;
import io.swagger.v3.oas.annotations.media.Schema;

public record FindBookmarkListRequest(
        @Schema(description = "폴더 번호", example = "1")
        Long folderNo
) {
    @Override
    public Long folderNo() {
        return folderNo;
    }
}
