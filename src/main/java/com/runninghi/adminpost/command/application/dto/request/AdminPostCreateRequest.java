package com.runninghi.adminpost.command.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.UUID;

public record AdminPostCreateRequest(

        @Schema(description = "작성자 고유 키" , example = "b583953c-cb27-43db-8e8d-d9cc230e2695")
        UUID userKey,

        @NotBlank(message = "썸네일 사진을 등록해주세요.")
        @Schema(description = "관리자 게시글 썸네일", example = "example.jpg")
        String thumbnail,

        @Schema(description = "관리자 게시글 제목", example = "example test")
        @NotBlank(message = "제목을 입력해주세요.")
        String adminPostTitle,

        @Schema(description = "관리자 게시글 내용", example = "example content")
        @NotBlank(message = "게시글 내용을 입력해주세요.")
        String adminPostContent,

        @Schema(description = "키워드 목록")
        List<KeywordListRequest> keywordList

){
}
