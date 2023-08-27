package com.runninghi.image.command.application.controller;

import com.runninghi.image.command.application.dto.request.ImageCreateRequest;
import com.runninghi.image.command.application.dto.request.ImageDeleteRequest;
import com.runninghi.image.command.application.dto.response.ImageCreateResponse;
import com.runninghi.image.command.application.service.ImageCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class ImageCommandController {

    private final ImageCommandService imageCommandService;

    // 이미지 업로드
    @PostMapping("/image")
    public ResponseEntity createImage(@RequestParam("file") MultipartFile multipartFile) {

        ImageCreateResponse imageUrl = imageCommandService.uploadImageFile(multipartFile);

        return new ResponseEntity<>(imageUrl, HttpStatus.OK);
    }

    // 이미지 삭제
    @DeleteMapping("/image")
    public ResponseEntity deleteImage(@RequestBody ImageDeleteRequest imageDeleteRequest) {

        imageCommandService.deleteImageFile(imageDeleteRequest);

        return new ResponseEntity<>("삭제 완료", HttpStatus.OK);
    }
}
