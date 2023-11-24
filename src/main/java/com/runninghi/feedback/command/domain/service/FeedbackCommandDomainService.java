package com.runninghi.feedback.command.domain.service;

import com.runninghi.common.annotation.DomainService;
import com.runninghi.common.handler.feedback.customException.IllegalArgumentException;
import com.runninghi.feedback.command.application.dto.response.FeedbackUserResponse;
import com.runninghi.feedback.command.domain.aggregate.entity.Feedback;
import com.runninghi.member.command.domain.aggregate.entity.enumtype.Role;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class FeedbackCommandDomainService {

    // 피드백 수정/삭제 요청자와 피드백 작성자가 동일한지 확인
    public boolean isWriter(FeedbackUserResponse user, Feedback feedback) {

        return user.id().equals(feedback.getFeedbackWriterVO().getFeedbackWriterId());

    }

    // 요청자가 관리자인지 확인
    public boolean isAdminRole(FeedbackUserResponse user) {

        return user.role().equals(Role.ADMIN);

    }

    // 피드백 작성 시 제한 사항 확인
    public void checkFeedbackValidation(String title, String content) {

        if (title.length() > 500) {
            throw new IllegalArgumentException("제목은 500자를 넘을 수 없습니다.");
        }

        if (title.length() == 0) {
            throw new IllegalArgumentException("제목은 1글자 이상이어야 합니다.");
        }

        if (content.length() == 0) {
            throw new IllegalArgumentException("내용은 1글자 이상이어야 합니다.");
        }

    }

}
