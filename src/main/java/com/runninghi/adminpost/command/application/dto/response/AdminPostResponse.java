package com.runninghi.adminpost.command.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.UUID;

public record AdminPostResponse (

        @Schema(description = "작성자 고유 키")
        UUID userKey,

        @Schema(description = "관리자 게시글 썸네일")
        String thumbnail,

        @Schema(description = "관리자 게시글 제목")
        String adminPostTitle,

        @Schema(description = "관리자 게시글 내용")
        String adminPostContent,

        @Schema(description = "키워드 번호 목록")
        List<Long> keywordNoList
)
{
        public static AdminPostResponse of(UUID userKey, String thumbnail, String admminPostTitle,
                                           String adminPostContent, List<Long> keywordNoList) {
                return new AdminPostResponse(
                        userKey,
                        thumbnail,
                        admminPostTitle,
                        adminPostContent,
                        keywordNoList
                );
        }
}
