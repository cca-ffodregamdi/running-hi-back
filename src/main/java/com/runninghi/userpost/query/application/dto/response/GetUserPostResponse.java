package com.runninghi.userpost.query.application.dto.response;


import com.runninghi.bookmark.command.domain.aggregate.entity.Bookmark;
import com.runninghi.bookmark.command.domain.aggregate.vo.BookmarkUserVO;
import com.runninghi.user.command.domain.aggregate.entity.User;
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
        Boolean isMe,
        Boolean bookmarkStatus,
        List<UserPostComment> comments
) {

    public static GetUserPostResponse from(UserPost userPost, List<UserPostImage> images, List<UserPostComment> comments,
                                           User user, Bookmark bookmark) {

        Boolean isMe = user.getId().equals(userPost.getUserVO().getUserId());
        Boolean bookmarkStatus = bookmark.getUserNoVO().equals(user.getId());

        return new GetUserPostResponse(
                userPost.getUserPostNo(),
                userPost.getUserPostDate(),
                userPost.getUserPostTitle(),
                userPost.getUserPostContent(),
                userPost.getUserVO().getUserId(),
                user.getNickname(),
                images,
                isMe,
                bookmarkStatus,
                comments
        );
    }
}
