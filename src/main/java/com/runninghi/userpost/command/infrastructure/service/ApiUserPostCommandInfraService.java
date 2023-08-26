package com.runninghi.userpost.command.infrastructure.service;

import com.runninghi.common.annotation.InfraService;
import com.runninghi.user.command.application.dto.user.response.UserInfoResponse;
import com.runninghi.user.query.application.service.UserQueryService;
import com.runninghi.userpost.command.application.dto.response.UserPostUserResponse;
import com.runninghi.userpost.command.domain.service.ApiUserPostCommandDomainService;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@InfraService
@RequiredArgsConstructor
public class ApiUserPostCommandInfraService implements ApiUserPostCommandDomainService {

private final UserQueryService userQueryService;

    public UserPostUserResponse checkUser(UUID userId) {

        UserInfoResponse result = userQueryService.findUserInfo(userId);

        return new UserPostUserResponse(
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
