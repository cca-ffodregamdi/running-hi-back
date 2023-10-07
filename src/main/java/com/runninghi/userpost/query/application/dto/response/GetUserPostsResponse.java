package com.runninghi.userpost.query.application.dto.response;

import com.runninghi.keywordofpost.command.domain.aggregate.entity.KeywordOfPost;

import java.util.List;
import java.util.UUID;

public record GetUserPostsResponse(
        UUID userId,
        Long userPostNo,
        String userPostTitle,
        int bookmarkCnt,
        boolean bookmarkStatus,
        List<KeywordOfPost> keywords
) {
}
