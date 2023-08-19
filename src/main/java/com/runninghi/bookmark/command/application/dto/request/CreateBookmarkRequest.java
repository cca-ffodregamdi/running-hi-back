package com.runninghi.bookmark.command.application.dto.request;

import com.runninghi.bookmark.command.domain.aggregate.vo.BookmarkVO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record CreateBookmarkRequest(
        @Schema(description = "회원 고유키", example = "{\"folderNo\": 1, \"postNo\": 123}")
        BookmarkVO bookmarkVO,
        @Schema(description = "회원 고유키", example = "c0a80121-7aeb-4b4b-8b0a-6b1c032f0e4a")
        UUID userId

) {
        @Override
        public BookmarkVO bookmarkVO() {
            return bookmarkVO;
        }

        @Override
        public UUID userId() {
            return userId;
        }
}
