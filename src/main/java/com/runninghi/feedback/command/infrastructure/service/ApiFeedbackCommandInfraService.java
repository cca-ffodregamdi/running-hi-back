package com.runninghi.feedback.command.infrastructure.service;

import com.runninghi.common.annotation.InfraService;
import com.runninghi.feedback.command.application.dto.response.FeedbackUserResponse;
import com.runninghi.feedback.command.domain.service.ApiFeedbackCommandDomainService;
import com.runninghi.member.query.application.dto.member.response.MemberInfoResponse;
import com.runninghi.member.query.application.service.MemberQueryService;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@InfraService
@RequiredArgsConstructor
public class ApiFeedbackCommandInfraService implements ApiFeedbackCommandDomainService {

    private final MemberQueryService userQueryService;

    public FeedbackUserResponse checkUser(UUID userId) {

        MemberInfoResponse result = userQueryService.findMemberInfo(userId);

        return new FeedbackUserResponse(
                result.id(),
                result.role(),
                result.name(),
                result.account(),
                result.createdAt()
        );
    }

}
