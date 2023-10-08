package com.runninghi.adminpost.command.application.controller;

import com.runninghi.adminpost.command.application.dto.request.AdminPostCreateRequest;
import com.runninghi.adminpost.command.application.dto.response.AdminPostResponse;
import com.runninghi.adminpost.command.application.service.AdminPostCommandService;
import com.runninghi.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.ConstraintDeclarationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Set;

@Tag(name = "관리자 게시글 Command API")
@RestController
@RequiredArgsConstructor
public class AdminPostCommandController {

    private final AdminPostCommandService adminPostCommandService;
    private final Validator validator;

    /**
     * 관리자 권한으로 관리자 코스 추천 게시글 작성
     * @param request 작성자 식별 값 / 제목 / 내용 / 키워드 목록
     * @param bindingResult request 유효성 체크 결과
     * @return 상태 코드 / 메세지 / 데이터{작성자 / 썸네일 / 제목 / 내용 / 키워드 목록}
     */
    @Operation(summary = "관리자 게시글 생성")
    @PostMapping("api/v1/admin-post")
    public ResponseEntity<ApiResponse> createAdminPost(@RequestBody @Valid AdminPostCreateRequest request,
                                                       BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ApiResponse.error(bindingResult.getFieldError().getDefaultMessage()));
        }
        Optional.ofNullable(request.userKey())
                .orElseThrow( () -> new NullPointerException("로그인 후 이용해주세요."));
        AdminPostResponse response = adminPostCommandService.createAdminPost(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.success("성공적으로 저장되었습니다.", response)
        );
    }
}
