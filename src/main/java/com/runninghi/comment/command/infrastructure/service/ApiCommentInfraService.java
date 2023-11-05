package com.runninghi.comment.command.infrastructure.service;

import com.runninghi.comment.command.domain.service.ApiCommentDomainService;
import com.runninghi.comment.query.application.dto.request.FindCommentRequest;
import com.runninghi.comment.query.application.service.CommentQueryService;
import com.runninghi.common.annotation.InfraService;
import com.runninghi.user.command.application.dto.user.response.UserInfoResponse;
import com.runninghi.user.query.application.service.UserQueryService;
import lombok.RequiredArgsConstructor;

import java.util.NoSuchElementException;
import java.util.UUID;


@InfraService
@RequiredArgsConstructor
public class ApiCommentInfraService implements ApiCommentDomainService {
    private final CommentQueryService commentQueryService;
    private final UserQueryService userQueryService;

    @Override
    public void validateCommentContentNull(String commentContent) {
        if (commentContent == null || commentContent.trim().isEmpty()) {
            throw new IllegalArgumentException("댓글은 공백일 수 없습니다.");
        }
    }

    @Override
    public void validateUser(UUID userNo) {

        UserInfoResponse result = userQueryService.findUserInfo(userNo);

        if(result == null) throw new NoSuchElementException("존재하지 않는 회원입니다.");

    }

    @Override
    public void validateUserPost(Long userPostNo) {


    }

    @Override
    public void validateComment(Long commentNo) {
        commentQueryService.findComment(new FindCommentRequest((commentNo)));
    }
}
