package com.runninghi.keyword.command.application.controller;

import com.runninghi.common.response.ApiResponse;
import com.runninghi.keyword.command.application.dto.request.KeywordCreateRequest;
import com.runninghi.keyword.command.application.dto.response.KeywordCreateResponse;
import com.runninghi.keyword.command.application.dto.response.UserCheckResponse;
import com.runninghi.keyword.command.application.service.KeywordService;
import com.runninghi.keyword.query.application.dto.GetKeywordListResponse;
import com.runninghi.keyword.query.application.service.QueryKeywordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Tag(name = "키워드 커멘드 컨트롤러")
@RestController
@RequiredArgsConstructor
public class KeywordController {

    private final QueryKeywordService queryKeywordService;
    private final KeywordService keywordService;

    @Operation(description = "키워드 생성")
    @PostMapping
    public ResponseEntity<ApiResponse> createKeyword(KeywordCreateRequest request) {
        Optional.ofNullable(request.userKey())
                .orElseThrow( () -> new NullPointerException("로그인 후 이용해주세요."));
        keywordService.checkAdminByUserKey(request.userKey());  // 관리자 아닐 시 예외
        KeywordCreateResponse keyword = keywordService.createKeyword(request);

        return ResponseEntity.ok(ApiResponse.success("성공적으로 등록되었습니다.", keyword));
    }

    @Operation(description = "전체 키워드 목록 조회")
    @GetMapping
    public ResponseEntity<List<GetKeywordListResponse>> findKeywordList() {
        List<GetKeywordListResponse> keywordList = queryKeywordService.getKeywordList();
        return ResponseEntity.ok(keywordList);
    }

}
