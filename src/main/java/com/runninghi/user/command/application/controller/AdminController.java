package com.runninghi.user.command.application.controller;


import com.runninghi.common.annotation.AdminAuthorize;
import com.runninghi.user.command.application.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "관리자용 API")
@RequiredArgsConstructor
@AdminAuthorize
@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    @Operation(summary = "회원 목록 조회")
    @GetMapping("/users")
    public ApiResponse getAllMembers() {
        return ApiResponse.success(adminService.getUsers());
    }

    @Operation(summary = "관리자 목록 조회")
    @GetMapping("/admins")
    public ApiResponse getAllAdmins() {
        return ApiResponse.success(adminService.getAdmins());
    }
}
