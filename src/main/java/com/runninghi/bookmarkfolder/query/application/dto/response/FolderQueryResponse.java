package com.runninghi.bookmarkfolder.query.application.dto.response;

import com.runninghi.bookmarkfolder.command.application.dto.response.FolderCommandResponse;
import com.runninghi.bookmarkfolder.command.domain.aggregate.entity.BookmarkFolder;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record FolderQueryResponse(
        @Schema(description = "폴더 고유 번호", example = "1")
        Long folderNo,
        @Schema(description = "폴더 이름", example = "러닝코스")
        String folderName,
        @Schema(description = "회원 고유키", example = "c0a80121-7aeb-4b4b-8b0a-6b1c032f0e4a")
        UUID userNo
) {
    public static FolderQueryResponse from(BookmarkFolder folder) {
        return new FolderQueryResponse(
                folder.getFolderNo(),
                folder.getFolderName(),
                folder.getUserNo()
        );
    }
}
