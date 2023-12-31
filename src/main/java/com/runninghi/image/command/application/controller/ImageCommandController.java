package com.runninghi.image.command.application.controller;

import com.runninghi.image.command.application.dto.request.ImageCreateRequest;
import com.runninghi.image.command.application.dto.request.ImagesCreateRequest;
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

    // 이미지 스토리지에 업로드, 이미지 entity 생성
    @PostMapping("/image")
    public ResponseEntity<?> createImage(@RequestPart MultipartFile file, @RequestPart ImageCreateRequest request) {

        ImageCreateResponse imageUrl = imageCommandService.uploadImageFile(file, request.board());

        return new ResponseEntity<>(imageUrl, HttpStatus.OK);
    }

    // 여러 개의 이미지 스토리지에 업로드, 이미지 entity 생성
    @PostMapping("/images")
    public ResponseEntity<?> createImages(@RequestPart List<MultipartFile> files, @RequestPart ImagesCreateRequest request) {

        List<ImageCreateResponse> imageUrl = imageCommandService.uploadImageFiles(files, request.board());

        return new ResponseEntity<>(imageUrl, HttpStatus.OK);
    }

    // 스토리지의 이미지 삭제, 이미지 entity 삭제
    @DeleteMapping("/image")
    public ResponseEntity<?> deleteImage(@RequestBody ImageDeleteRequest request) {

        imageCommandService.deleteImageFile(request);

        return new ResponseEntity<>("삭제 완료", HttpStatus.OK);
    }

}
