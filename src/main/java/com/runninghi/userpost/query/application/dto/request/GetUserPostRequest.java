package com.runninghi.userpost.query.application.dto.request;

import java.util.UUID;

public record GetUserPostRequest(
        UUID userId
) {
}
