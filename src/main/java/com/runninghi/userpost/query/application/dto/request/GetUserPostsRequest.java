package com.runninghi.userpost.query.application.dto.request;

import java.util.UUID;

public record GetUserPostsRequest(
        UUID userId
) {
}
