package com.runninghi.feedback.command.infrastructure.service;

import com.runninghi.common.annotation.InfraService;
import com.runninghi.feedback.command.application.dto.response.FeedbackUserResponse;
import com.runninghi.feedback.command.domain.service.ApiFeedbackCommandDomainService;
import com.runninghi.user.query.application.dto.user.response.UserInfoResponse;
import com.runninghi.user.query.application.service.UserQueryService;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@InfraService
@RequiredArgsConstructor
public class ApiFeedbackCommandInfraService implements ApiFeedbackCommandDomainService {

    private final UserQueryService userQueryService;

    public FeedbackUserResponse checkUser(UUID userId) {

        UserInfoResponse result = userQueryService.findUserInfo(userId);

        return new FeedbackUserResponse(
                result.id(),
                result.role(),
                result.name(),
                result.account(),
                result.createdAt()
        );
    }

}
