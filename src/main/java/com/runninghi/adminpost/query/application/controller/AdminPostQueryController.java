package com.runninghi.adminpost.query.application.controller;

import com.google.protobuf.Api;
import com.runninghi.adminpost.query.application.dto.request.QueryParameterRequest;
import com.runninghi.adminpost.query.application.dto.response.AdminPostQueryResponse;
import com.runninghi.adminpost.query.application.service.AdminPostQueryService;
import com.runninghi.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "관리자 게시글 Query API")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/admin-post")
public class AdminPostQueryController {

    private final AdminPostQueryService adminPostQueryService;

//    @Operation(summary = "관리자 게시글 전체 조화")
//    @GetMapping
//    public ResponseEntity<ApiResponse> getAdminPostsList (@PageableDefault(page = 0, size = 12, sort = "adminPostNo", direction = Sort.Direction.DESC)Pageable pageable) {
//        Page<AdminPostQueryResponse> adminPostsList = adminPostQueryService.getAdminPostsList(pageable);
//        return ResponseEntity.ok().body(ApiResponse.success("관리자 게시글 전체 조회에 성공하였습니다.", adminPostsList));
//    }

    @Operation(summary = "관리자 게시글 전체 조회")
    @GetMapping
    public ResponseEntity<ApiResponse> getAdminPostsList (QueryParameterRequest request) {

        int offset = (request.page() - 1) * request.postLimit();    // 0 10 20
        int startIndex  = offset + 1;   // 1 11 21
        int endIndex = offset + request.postLimit();    // 10 20 30

        Long totalCnt = adminPostQueryService.getAdminPostTotalCnt();
        List<AdminPostQueryResponse> adminPostsList = adminPostQueryService.getAdminPostsList(request);




    }

}
