package com.runninghi.keyword.query.application.controller;

import com.runninghi.keyword.query.application.dto.response.FindKeywordResponse;
import com.runninghi.keyword.query.application.service.KeywordQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "키워드 쿼리 컨트롤러")
@RestController
@RequiredArgsConstructor
public class KeywordQueryController {

    private final KeywordQueryService keywordQueryService;

    @Operation(summary = "전체 키워드 목록 조회")
    @GetMapping("api/v1/keyword")
    public ResponseEntity<List<FindKeywordResponse>> findKeywordList() {
        List<FindKeywordResponse> keywordList = keywordQueryService.findKeywordList();
        return ResponseEntity.ok(keywordList);
    }

}
