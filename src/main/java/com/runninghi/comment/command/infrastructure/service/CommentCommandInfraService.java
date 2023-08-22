package com.runninghi.comment.command.infrastructure.service;

import com.runninghi.comment.command.domain.service.CommentCommandDomainService;
import com.runninghi.comment.query.application.dto.request.FindCommentRequest;
import com.runninghi.comment.query.application.service.CommentQueryService;
import com.runninghi.common.annotation.InfraService;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@InfraService
@RequiredArgsConstructor
public class CommentCommandInfraService implements CommentCommandDomainService {

    private final CommentQueryService commentQueryService;

    @Override
    public void validateCommentContentNull(String commentContent) {
        if (commentContent == null || commentContent.trim().isEmpty()) {
            throw new IllegalArgumentException("댓글은 공백일 수 없습니다.");
        }
    }

    @Override
    public void validateUser(UUID userNo) {
        //회원 신고횟수에 따른 댓글 작성 여부 판단 로직
    }

    @Override
    public void validateUserPost(Long userPostNo) {
        //존재하는 게시글 번호인지 확인
        //사용자의 게시글인지 확인
    }

    @Override
    public void validateComment(Long commentNo) {
        commentQueryService.findComment(new FindCommentRequest((commentNo)));
    }

}
