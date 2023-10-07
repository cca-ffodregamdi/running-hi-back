package com.runninghi.keywordofpost.command.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record KeywordOfPostCreateRequest (
        @Schema(description = "관리자 게시글 번호")
        Long adminPostNo,

        @Schema(description = "유저 게시글 번호")
        Long userPostNo,

        @Schema(description = "키워드 번호")
        Long keywordNo,

        @Schema(description = "키워드 이름")
        String keywordName
)
{

    public static KeywordOfPostCreateRequest adminPostFrom (Long adminPostNo, Long keywordNo, String keywordName){
        return new KeywordOfPostCreateRequest(adminPostNo, null, keywordNo, keywordName);
    }

    public static KeywordOfPostCreateRequest userPostFrom (Long userPostNo, Long keywordNo, String keywordName){
        return new KeywordOfPostCreateRequest(userPostNo, null, keywordNo, keywordName);
    }
}
