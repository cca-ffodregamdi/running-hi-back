package com.runninghi.bookmark.command.application.dto.request;

import com.runninghi.bookmark.command.domain.aggregate.vo.BookmarkVO;
import io.swagger.v3.oas.annotations.media.Schema;

public record DeleteBookmarkRequest(
        @Schema(description = "회원 고유키", example = "{\"folderNo\": 1, \"postNo\": 123}")
        BookmarkVO bookmarkVO
) {
    @Override
    public BookmarkVO bookmarkVO() {
        return bookmarkVO;
    }
}
