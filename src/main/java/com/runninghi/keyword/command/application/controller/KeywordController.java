package com.runninghi.keyword.command.application.controller;

import com.runninghi.keyword.command.application.service.KeywordService;
import com.runninghi.keyword.query.application.dto.GetKeywordListResponse;
import com.runninghi.keyword.query.application.service.QueryKeywordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "키워드")
@RestController
@RequiredArgsConstructor
public class KeywordController {

    private final QueryKeywordService queryKeywordService;
    private final KeywordService keywordService;

    @Operation(description = "키워드 생성")
    @PostMapping
    public void createKeyword(@RequestBody String keywordName) {
        keywordService.createKeyword(keywordName);
    }


    @Operation(description = "전체 키워드 목록 조회")
    @GetMapping
    public ResponseEntity<List<GetKeywordListResponse>> getKeywordList() {
        List<GetKeywordListResponse> keywordList = queryKeywordService.getKeywordList();
        return ResponseEntity.ok(keywordList);
    }

}
