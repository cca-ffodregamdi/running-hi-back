package com.runninghi.bookmark.command.application.controller;

import com.runninghi.bookmark.command.application.dto.request.CreateBookmarkRequest;
import com.runninghi.bookmark.command.application.dto.request.DeleteBookmarkRequest;
import com.runninghi.bookmark.command.application.dto.response.BookmarkCommandResponse;
import com.runninghi.bookmark.command.application.dto.response.BookmarkDeleteResponse;
import com.runninghi.bookmark.command.application.service.BookmarkCommandService;
import com.runninghi.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "즐겨찾기 Command API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/bookmark")
public class BookmarkCommandController {

    private final BookmarkCommandService bookmarkCommandService;

    @Operation(summary = "즐겨찾기 폴더 생성 ")
    @PostMapping("/api/v1/bookmarks")
    public ResponseEntity<ApiResponse> createBookmark(CreateBookmarkRequest request) {

        BookmarkCommandResponse response = bookmarkCommandService.createBookmark(request);

        return ResponseEntity.ok(ApiResponse.success("성공적으로 등록되었습니다.", response));
    }

    @Operation(summary = "즐겨찾기 폴더 생성 ")
    @DeleteMapping("/api/v1/bookmarks")
    public ResponseEntity<ApiResponse> deleteBookmark(DeleteBookmarkRequest request) {

        BookmarkDeleteResponse response = bookmarkCommandService.deleteBookmark(request);

        return ResponseEntity.ok(ApiResponse.success("성공적으로 등록되었습니다.", response));
    }

}
