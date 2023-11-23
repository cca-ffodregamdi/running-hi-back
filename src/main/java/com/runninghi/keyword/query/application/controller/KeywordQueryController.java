package com.runninghi.keyword.query.application.controller;

import com.runninghi.common.response.ApiResponse;
import com.runninghi.keyword.query.application.dto.response.FindKeywordResponse;
import com.runninghi.keyword.query.application.service.KeywordQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "키워드 Query API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/keyword")
public class KeywordQueryController {

    private final KeywordQueryService keywordQueryService;

    @Operation(summary = "키워드 전체 목록 조회")
    @GetMapping
    public ResponseEntity<List<FindKeywordResponse>> findKeywordList() {
        List<FindKeywordResponse> keywordList = keywordQueryService.findKeywordList();
        return ResponseEntity.ok(keywordList);
    }

    @Operation(summary = "키워드 선택 조회")
    @GetMapping("/{keywordNo}")
    public ResponseEntity<ApiResponse> findKeyword (@PathVariable(name = "keywordNo") Long keywordNo) {
        FindKeywordResponse response = keywordQueryService.findKeywordByKeywordNo(keywordNo);
        return ResponseEntity.ok().body(ApiResponse.success("키워드 조회에 성공하였습니다.", response));
    }

}
