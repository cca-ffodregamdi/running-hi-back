package com.runninghi.userpost.command.application.dto.request;

import java.util.List;
import java.util.UUID;

public record UserPostCreateRequest(
        UUID userId,
        String userPostTitle,
        String userPostContent,
        List<KeywordListRequest> keywordList
) {
}
