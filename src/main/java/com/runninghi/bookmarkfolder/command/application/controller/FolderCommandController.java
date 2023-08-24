package com.runninghi.bookmarkfolder.command.application.controller;

import com.runninghi.bookmarkfolder.command.application.dto.request.CreateFolderRequest;
import com.runninghi.bookmarkfolder.command.application.dto.request.DeleteFolderRequest;
import com.runninghi.bookmarkfolder.command.application.dto.request.UpdateFolderRequest;
import com.runninghi.bookmarkfolder.command.application.dto.response.FolderCommandResponse;
import com.runninghi.bookmarkfolder.command.application.dto.response.FolderDeleteResponse;
import com.runninghi.bookmarkfolder.command.application.service.BookmarkFolderCommandService;
import com.runninghi.comment.command.application.dto.request.CreateCommentRequest;
import com.runninghi.comment.command.application.dto.request.DeleteCommentRequest;
import com.runninghi.comment.command.application.dto.request.UpdateCommentRequest;
import com.runninghi.comment.command.application.dto.response.CommentCommandResponse;
import com.runninghi.comment.command.application.dto.response.CommentDeleteResponse;
import com.runninghi.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "즐겨찾기 폴더 커맨드 컨트롤러")
@RestController
@RequiredArgsConstructor
public class FolderCommandController {

    private final BookmarkFolderCommandService folderCommandService;

    @Operation(summary = "즐겨찾기 폴더 생성 ")
    @PostMapping("/api/v1/bookmark-folders")
    public ResponseEntity<ApiResponse> createBookmarkFolder(CreateFolderRequest request) {

        FolderCommandResponse response = folderCommandService.createNewBookmarkFolder(request);

        return ResponseEntity.ok(ApiResponse.success("성공적으로 등록되었습니다.", response));
    }

    @Operation(summary = "즐겨찾기 폴더 삭제")
    @DeleteMapping("/api/v1/bookmark-folders/{folderNo}")
    public ResponseEntity<ApiResponse> deleteBookmarkFolder(DeleteFolderRequest request) {

        FolderDeleteResponse response = folderCommandService.deleteBookmarkFolder(request);

        return ResponseEntity.ok(ApiResponse.success("성공적으로 삭제되었습니다.", response));
    }

    @Operation(summary = "즐겨찾기 폴더 수정")
    @PutMapping("/api/v1/bookmark-folders/{folderNo}")
    public ResponseEntity<ApiResponse> updateBookmarkFolder(UpdateFolderRequest request) {

        FolderCommandResponse response = folderCommandService.updateBookmarkFolder(request);

        return ResponseEntity.ok(ApiResponse.success("성공적으로 수정되었습니다.", response));
    }
}
