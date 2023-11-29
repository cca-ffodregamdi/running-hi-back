package com.runninghi.bookmark.command.application.dto.response;

import com.runninghi.bookmark.command.domain.aggregate.entity.Bookmark;
import com.runninghi.bookmark.command.domain.aggregate.vo.BookmarkMemberVO;
import com.runninghi.bookmark.command.domain.aggregate.vo.BookmarkVO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public record BookmarkCommandResponse(
        @Schema(description = "회원 고유키", example = "{\"folderNo\": 1, \"postNo\": 123}")
        BookmarkVO bookmarkVO,
        @Schema(description = "회원 고유키", example = "c0a80121-7aeb-4b4b-8b0a-6b1c032f0e4a")
        BookmarkMemberVO memberNo,

        @Schema(description = "즐겨찾기 저장 날짜", example = "2023-09-09")
        LocalDate addDate
) {

    public static BookmarkCommandResponse from(Bookmark bookmark) {
        return new BookmarkCommandResponse(
                bookmark.getBookmarkVO(),
                bookmark.getMemberNoVO(),
                bookmark.getAddDate()
        );
    }

}
