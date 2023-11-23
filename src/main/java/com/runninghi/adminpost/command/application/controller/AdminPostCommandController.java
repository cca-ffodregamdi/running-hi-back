package com.runninghi.adminpost.command.application.controller;

import com.runninghi.adminpost.command.application.dto.request.AdminPostRequestDTO;
import com.runninghi.adminpost.command.application.dto.response.AdminPostResponse;
import com.runninghi.adminpost.command.application.service.AdminPostCommandService;
import com.runninghi.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Tag(name = "관리자 게시글 Command API")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/admin-post")
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
    @PostMapping
    public ResponseEntity<ApiResponse> createAdminPost(@RequestBody @Valid AdminPostRequestDTO request,
                                                       BindingResult bindingResult,
                                                       @RequestParam(value = "thumbnail")MultipartFile thumbnail,
                                                       @RequestParam(value = "gpxFile")MultipartFile gpxFile) throws IOException {
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ApiResponse.error(bindingResult.getFieldError().getDefaultMessage()));
        }
        Optional.ofNullable(request.getUserKey())
                .orElseThrow( () -> new NullPointerException("로그인 후 이용해주세요."));
        request.setThumbnail(thumbnail);
        request.setGpxFile(gpxFile);
        AdminPostResponse response = adminPostCommandService.createAdminPost(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.success("성공적으로 저장되었습니다.", response)
        );
    }

    /**
     * 관리자 권한으로 관리자 코스 추천 게시글 수정
     * @param request - 작성자 식별 값 / 제목 / 내용 / 키워드 목록
     * @param bindingResult - request 유효성 체크 결과
     * @return ResponseEntity<ApiResponse> - 상태 코드 / 메세지 / 데이터
     */
    @Operation(summary = "관리자 게시글 수정")
    @PutMapping("/{adminPostNo}")
    public ResponseEntity<ApiResponse> updateAdminPost(@PathVariable(name = "adminPostNo") Long adminPostNo,
                                                       @RequestBody @Valid AdminPostRequestDTO request,
                                                       BindingResult bindingResult,
                                                       @RequestParam(value = "thumbnail")MultipartFile thumbnail,
                                                       @RequestParam(value = "gpxFile")MultipartFile gpxFile) throws IOException {
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ApiResponse.error(bindingResult.getFieldError().getDefaultMessage()));
        }
        Optional.ofNullable(request.getUserKey())
                .orElseThrow( () -> new NullPointerException("로그인 후 이용해주세요."));
        request.setThumbnail(thumbnail);
        request.setGpxFile(gpxFile);
        AdminPostResponse response = adminPostCommandService.updateAdminPost(adminPostNo, request);

        return ResponseEntity.ok().body(ApiResponse.success("성공적으로 저장되었습니다.", response));
    }

    @Operation(summary = "관리자 게시글 삭제")
    @DeleteMapping("/{adminPostNo}")
    public ResponseEntity<ApiResponse> deleteAdminPost(@PathVariable(name = "adminPostNo") Long adminPostNo) {

        adminPostCommandService.deleteAdminPost(adminPostNo);

        return ResponseEntity.ok().body(ApiResponse.success("관리자 게시글 삭제에 성공하였습니다.", null));
    }


}
