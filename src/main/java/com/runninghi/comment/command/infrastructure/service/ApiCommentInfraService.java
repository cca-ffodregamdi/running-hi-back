package com.runninghi.comment.command.infrastructure.service;

import com.runninghi.comment.command.domain.service.ApiCommentDomainService;
import com.runninghi.comment.query.application.dto.request.FindCommentRequest;
import com.runninghi.comment.query.application.service.CommentQueryService;
import com.runninghi.common.annotation.InfraService;
import com.runninghi.member.query.application.dto.member.response.MemberInfoResponse;
import com.runninghi.member.query.application.service.MemberQueryService;
import lombok.RequiredArgsConstructor;

import java.util.NoSuchElementException;
import java.util.UUID;


@InfraService
@RequiredArgsConstructor
public class ApiCommentInfraService implements ApiCommentDomainService {
    private final CommentQueryService commentQueryService;
    private final MemberQueryService userQueryService;

    @Override
    public void validateCommentContentNull(String commentContent) {
        if (commentContent == null || commentContent.trim().isEmpty()) {
            throw new IllegalArgumentException("댓글은 공백일 수 없습니다.");
        }
    }

    @Override
    public void validateMember(UUID userNo) {

        MemberInfoResponse result = userQueryService.findMemberInfo(userNo);

        if (result == null) throw new NoSuchElementException("존재하지 않는 회원입니다.");

    }

    @Override
    public void validateMemberPost(Long memberPostNo) {


    }

    @Override
    public void validateComment(Long commentNo) {
        commentQueryService.findComment(new FindCommentRequest((commentNo)));
    }
}
