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

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ImageCommandController {

    private final ImageCommandService imageCommandService;

//    // 이미지 s3에 업로드, 이미지 entity 생성
//    @PostMapping("/image")
//    public ResponseEntity<?> createImage(@RequestParam("file") MultipartFile multipartFile) {
//
//        ImageCreateResponse imageUrl = imageCommandService.uploadImageFile(multipartFile);
//
//        return new ResponseEntity<>(imageUrl, HttpStatus.OK);
//    }
//
//    // 여러 개의 이미지 s3에 업로드, 이미지 entity 생성
//    @PostMapping("/images")
//    public ResponseEntity<?> createImages(@ModelAttribute ImageCreateRequest imageCreateRequest) {
//
//        List<ImageCreateResponse> imageUrl = imageCommandService.uploadImageFiles(imageCreateRequest.files());
//
//        return new ResponseEntity<>(imageUrl, HttpStatus.OK);
//    }
//
//    // s3의 이미지 삭제, 이미지 entity 삭제
//    @DeleteMapping("/image")
//    public ResponseEntity<?> deleteImage(@RequestBody ImageDeleteRequest imageDeleteRequest) {
//
//        imageCommandService.deleteImageFile(imageDeleteRequest);
//
//        return new ResponseEntity<>("삭제 완료", HttpStatus.OK);
//    }

}
