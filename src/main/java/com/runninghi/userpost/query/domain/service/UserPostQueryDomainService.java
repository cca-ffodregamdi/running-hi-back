package com.runninghi.userpost.query.domain.service;

import com.runninghi.common.annotation.DomainService;
import com.runninghi.userpost.query.application.dto.response.UserPostUserResponse;
import com.runninghi.userpost.command.domain.aggregate.entity.UserPost;

@DomainService
public class UserPostQueryDomainService {

    // 유저 게시글 수정/삭제 요청자와 게시글 작성자가 동일한지 확인
    public boolean isWriter(UserPostUserResponse user, UserPost userPost) {

        return user.id().equals(userPost.getUserVO().getUserId());

    }
}
