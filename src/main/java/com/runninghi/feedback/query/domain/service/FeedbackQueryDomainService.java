package com.runninghi.feedback.query.domain.service;

import com.runninghi.common.annotation.DomainService;

import com.runninghi.feedback.command.domain.aggregate.entity.Feedback;
import com.runninghi.feedback.query.application.dto.response.FeedbackUserResponse;
import com.runninghi.user.command.domain.aggregate.entity.enumtype.Role;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class FeedbackQueryDomainService {

    // 피드백 수정/삭제 요청자와 피드백 작성자가 동일한지 확인
    public boolean isWriter(FeedbackUserResponse user, Feedback feedback) {

        return user.id().equals(feedback.getFeedbackWriterVO().getFeedbackWriterId());

    }

    // 요청자가 관리자인지 확인
    public boolean isAdminRole(FeedbackUserResponse user) {

        return user.role().equals(Role.ADMIN);

    }

}
