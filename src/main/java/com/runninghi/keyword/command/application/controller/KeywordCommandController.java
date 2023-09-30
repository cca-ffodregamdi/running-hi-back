package com.runninghi.keyword.command.application.controller;

import com.runninghi.common.response.ApiResponse;
import com.runninghi.keyword.command.application.dto.request.KeywordCreateRequest;
import com.runninghi.keyword.command.application.dto.request.KeywordUpdateRequest;
import com.runninghi.keyword.command.application.dto.response.KeywordResponse;
import com.runninghi.keyword.command.application.service.KeywordCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "키워드 Command API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/keyword")

public class KeywordCommandController {

    private final KeywordCommandService keywordCommandService;

    @Operation(summary = "키워드 생성")
    @PostMapping
    public ResponseEntity<ApiResponse> createKeyword(KeywordCreateRequest request) {
        Optional.ofNullable(request.userKey())
                .orElseThrow( () -> new NullPointerException("로그인 후 이용해주세요."));
        keywordCommandService.checkAdminByUserKey(request.userKey());  // 관리자 아닐 시 예외
        KeywordResponse response = keywordCommandService.createKeyword(request.keywordName());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("성공적으로 등록되었습니다.", response));
    }

    @Operation(summary = "키워드 수정")
    @PutMapping("/{keywordNo}")
    public ResponseEntity<ApiResponse> updateKeyword(@PathVariable(name = "keywordNo") Long keywordNo,
                                                     @RequestBody KeywordUpdateRequest request) {
        Optional.ofNullable(request.userKey())
                .orElseThrow( () -> new NullPointerException("로그인 후 이용해주세요."));
        KeywordResponse response = keywordCommandService.updateKeyword(keywordNo, request.keywordName());
        return ResponseEntity.ok(ApiResponse.success("성공적으로 수정되었습니다.", response));
    }

    @Operation(summary = "키워드 삭제")
    @DeleteMapping("/{keywordNo}")
    public ResponseEntity<ApiResponse> deleteKeyword(@PathVariable(name = "keywordNo") Long keywordNo) {
        int response = keywordCommandService.deleteKeyword(keywordNo);
        return ResponseEntity.ok(ApiResponse.success("성공적으로 삭제되었습니다.", response));
    }

}
