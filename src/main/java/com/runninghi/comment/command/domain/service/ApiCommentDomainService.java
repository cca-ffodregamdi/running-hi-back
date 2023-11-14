package com.runninghi.comment.command.domain.service;

import java.util.UUID;

public interface ApiCommentDomainService {
    void validateCommentContentNull(String commentContent);
    void validateUser(UUID userNo);
    void validateUserPost(Long userPostNo);
    void validateComment(Long commentNo);
}
