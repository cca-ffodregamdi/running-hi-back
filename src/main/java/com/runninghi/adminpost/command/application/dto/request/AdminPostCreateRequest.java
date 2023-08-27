package com.runninghi.adminpost.command.application.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.UUID;

public record AdminPostCreateRequest(
        @Schema(description = "작성자 고유 키" , example = "b583953c-cb27-43db-8e8d-d9cc230e2695")
        UUID userKey,

        @Schema(description = "관리자 게시글 썸네일", example = "example.jpg")
        String thumbnail,

        @Schema(description = "관리자 게시글 제목", example = "example test")
        String adminPostTitle,

        @Schema(description = "관리자 게시글 내용", example = "example content")
        String adminPostContent,

        @Schema(description = "키워드 목록", example = "[ { keywordNo: 1, keywordName: 쉬움}, {keywordNo:2, keywordName: 보통")
        List<KeywordListRequest> keywordList

){
}
