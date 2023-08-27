package com.runninghi.image.command.domain.service;

import com.runninghi.common.annotation.DomainService;
import com.runninghi.common.handler.feedback.customException.IllegalArgumentException;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@DomainService
@RequiredArgsConstructor
public class ImageCommandDomainService {

    // 파일 확장자의 유효성을 확인하고 파일 확장자 반환
    public String checkAndGetFileExtension(String fileName) {

        // 허용되는 이미지 확장자 목록
        List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "png", "bmp", "webp");

        // 파일의 확장자 추출
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));

        // 확장자가 없는 경우 예외 처리
        if (fileExtension.length() == 0) {
            throw new IllegalArgumentException("이미지 파일이 아닙니다.");
        }

        // 허용되는 확장자와 비교하여 이미지 파일 여부 확인
        if (allowedExtensions.contains(fileExtension.toLowerCase())) {
            throw new IllegalArgumentException("이미지 파일이 아닙니다.");
        }

        return fileExtension;
    }


}
