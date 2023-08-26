package com.runninghi.userpost.command.application.dto.response;

import com.runninghi.userpost.command.domain.aggregate.entity.UserPost;
import com.runninghi.userpost.command.domain.aggregate.vo.UserVO;

import java.util.Date;

public record UserPostResponse(
        Long userPostNo,
        Date userPostDate,
        String userPostTitle,
        String userPostContent,
        UserVO userVO
) {

    public static UserPostResponse from (UserPost userPost) {
        return new UserPostResponse(
                userPost.getUserPostNo(),
                userPost.getUserPostDate(),
                userPost.getUserPostTitle(),
                userPost.getUserPostContent(),
                userPost.getUserVO()
        );
    }
}
