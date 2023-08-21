package com.runninghi.comment.command.infrastructure.service;

import com.runninghi.comment.command.domain.service.CommentCommandDomainService;
import com.runninghi.common.annotation.InfraService;

import java.util.UUID;

@InfraService
public class CommentCommandInfraService implements CommentCommandDomainService {
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
}
