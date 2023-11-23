package com.runninghi.adminpost.command.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
public class AdminPostRequestDTO {

        @Schema(description = "작성자 고유 키" , example = "b583953c-cb27-43db-8e8d-d9cc230e2695")
        UUID userKey;

        @Schema(description = "관리자 게시글 썸네일")
        MultipartFile thumbnail;

        @Schema(description = "관리자 게시글 제목", example = "example title")
        @NotBlank(message = "제목을 입력해주세요.")
        String adminPostTitle;

        @Schema(description = "관리자 게시글 내용", example = "example content")
        @NotBlank(message = "게시글 내용을 입력해주세요.")
        String adminPostContent;

        @Schema(description = "gpx 파일")
        MultipartFile gpxFile;

        @Schema(description = "키워드 목록")
        List<KeywordListRequest> keywordList;
}
