package com.runninghi.image.command.application.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record ImageCreateRequest (
        MultipartFile image
){ }
