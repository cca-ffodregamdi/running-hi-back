package com.runninghi.feedback.query.infrastructure.service;

import com.runninghi.common.annotation.InfraService;
import com.runninghi.feedback.query.application.dto.response.FeedbackUserResponse;
import com.runninghi.feedback.query.domain.service.ApiFeedbackQueryDomainService;
import com.runninghi.user.query.application.dto.user.response.UserInfoResponse;
import com.runninghi.user.query.application.service.UserQueryService;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@InfraService
@RequiredArgsConstructor
public class ApiFeedbackInfraQueryDomainService implements ApiFeedbackQueryDomainService {

    private final UserQueryService userQueryService;

    public FeedbackUserResponse checkUser(UUID userId) {

        UserInfoResponse result = userQueryService.findUserInfo(userId);

        return new FeedbackUserResponse(
                result.id(),
                result.account(),
                result.name(),
                result.nickname(),
                result.email(),
                result.kakaoId(),
                result.kakaoName(),
                result.reportCount(),
                result.blackListStatus(),
                result.status(),
                result.role(),
                result.createdAt()
        );
    }

}
