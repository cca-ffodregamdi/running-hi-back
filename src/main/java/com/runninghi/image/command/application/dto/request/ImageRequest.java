package com.runninghi.image.command.application.dto.request;

import java.util.List;

public record ImageRequest(
        List<String> imageUrls,
        Long postNo
) { }
