package com.runninghi.user.command.application.controller;


import com.runninghi.common.annotation.AdminAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "관리자용 COMMAND API")
@RequiredArgsConstructor
@AdminAuthorize
@RestController
@RequestMapping("/admin")
public class AdminCommandController {

//    @Operation(summary = "회원 목록 조회")
//    @GetMapping("/users")
//    public ApiResponse getAllUsers() {
//        return ApiResponse.success("조회 성공", adminCommandService.findAllUsers());
//    }
//
//    @Operation(summary = "관리자 목록 조회")
//    @GetMapping("/admins")
//    public ApiResponse getAllAdmins() {
//        return ApiResponse.success("조회 성공", adminCommandService.findAllAdmins());
//    }
}
