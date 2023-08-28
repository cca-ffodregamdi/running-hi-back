package com.runninghi.image.command.application.dto.response;

import java.util.List;

public record ImageResponse(
        List<Long> imageNos
) { }
