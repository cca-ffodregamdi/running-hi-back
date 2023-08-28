package com.runninghi.image.command.application.dto.request;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record ImageCreateRequest (
        List<MultipartFile> files,
        String title,
        String content
){ }
