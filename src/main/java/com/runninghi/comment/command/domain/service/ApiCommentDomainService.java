package com.runninghi.comment.command.domain.service;

import java.util.UUID;

public interface ApiCommentDomainService {
    void validateCommentContentNull(String commentContent);

    void validateMember(UUID memberNo);

    void validateMemberPost(Long memberPostNo);

    void validateComment(Long commentNo);
}
