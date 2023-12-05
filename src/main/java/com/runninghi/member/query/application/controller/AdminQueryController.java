package com.runninghi.member.query.application.controller;

import com.runninghi.common.annotation.AdminAuthorize;
import com.runninghi.common.response.ApiResponse;
import com.runninghi.member.query.application.service.AdminQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "관리자용 QUERY API")
@RequiredArgsConstructor
@AdminAuthorize
@RestController
@RequestMapping("/api/v1/admin")
public class AdminQueryController {
    private final AdminQueryService adminQueryService;

    @Operation(summary = "회원 목록 조회")
    @GetMapping("/members")
    public ApiResponse getAllMembers() {
        return ApiResponse.success("조회 성공", adminQueryService.findAllMembers());
    }

    @Operation(summary = "관리자 목록 조회")
    @GetMapping("/admins")
    public ApiResponse getAllAdmins() {
        return ApiResponse.success("조회 성공", adminQueryService.findAllAdmins());
    }
}
