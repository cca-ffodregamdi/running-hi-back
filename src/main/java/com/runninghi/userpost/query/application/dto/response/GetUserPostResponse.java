package com.runninghi.userpost.query.application.dto.response;


import com.runninghi.userpost.command.domain.aggregate.entity.UserPost;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public record GetUserPostResponse(
        Long userPostNo,
        Date userPostDate,
        String userPostTitle,
        String userPostContent,
        UUID userNo,
        String nickname,
        List<UserPostImage> images,
        boolean isMe,
        boolean bookmarkStatus,
        List<UserPostComment> comments
) {

    public static GetUserPostResponse from(UserPost userPost, List<UserPostImage> images, List<UserPostComment> comments,
                                           boolean isMe, boolean bookmarkStatus, String nickname) {
        return new GetUserPostResponse(
                userPost.getUserPostNo(),
                userPost.getUserPostDate(),
                userPost.getUserPostTitle(),
                userPost.getUserPostContent(),
                userPost.getUserVO().getUserId(),
                nickname,
                images,
                isMe,
                bookmarkStatus,
                comments
        );
    }
}
