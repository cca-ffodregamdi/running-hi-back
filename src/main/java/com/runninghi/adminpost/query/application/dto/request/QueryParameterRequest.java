package com.runninghi.adminpost.query.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record QueryParameterRequest(

        @Schema(description = "현재 페이지", example = "1")
        int page,

        @Schema(description = "검색어", example = "경복궁")
        String search,

        @Schema(description = "이전 마지막 게시글 번호", example = "0")
        Long preAdminPostNo,

        @Schema(description = "페이지 내 최대 게시글 수", example = "12")
        int postLimit,

        @Schema(description = "전체 게시글 수", example = "1220")
        Long totalCnt

) {
}
