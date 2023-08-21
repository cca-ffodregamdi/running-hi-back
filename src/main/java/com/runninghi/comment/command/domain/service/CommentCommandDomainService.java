package com.runninghi.comment.command.domain.service;

import com.runninghi.common.annotation.DomainService;

import java.util.UUID;

public interface CommentCommandDomainService {
    void validateCommentContentNull(String commentContent);
    void validateUser(UUID userNo);
    void validateUserPost(Long userPostNo);
    void validateComment(Long commentNo);
}
