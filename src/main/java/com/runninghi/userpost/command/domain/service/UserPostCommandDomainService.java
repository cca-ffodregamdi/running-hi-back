package com.runninghi.userpost.command.domain.service;

import com.runninghi.common.annotation.DomainService;
import com.runninghi.common.handler.feedback.customException.IllegalArgumentException;
import com.runninghi.user.command.domain.aggregate.entity.enumtype.Role;
import com.runninghi.userpost.command.application.dto.response.UserPostUserResponse;
import com.runninghi.userpost.command.domain.aggregate.entity.UserPost;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class UserPostCommandDomainService {

    // 유저 게시글 수정/삭제 요청자와 게시글 작성자가 동일한지 확인
    public boolean isWriter(UserPostUserResponse user, UserPost userPost) {

        return user.id().equals(userPost.getUserVO().getUserId());

    }

    // 요청자가 관리자인지 확인
    public boolean isAdminRole(UserPostUserResponse user) {

        return user.role().equals(Role.ADMIN);

    }

    // 유저 게시글 작성 시 제한 사항 확인
    public void checkUserPostValidation(String title) {

        if (title.length() > 500) {
            throw new IllegalArgumentException("제목은 500자를 넘을 수 없습니다.");
        }

        if (title.length() == 0) {
            throw new IllegalArgumentException("제목은 1글자 이상이어야 합니다.");
        }

    }

}
