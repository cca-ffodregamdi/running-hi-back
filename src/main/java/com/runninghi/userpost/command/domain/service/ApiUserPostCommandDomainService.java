package com.runninghi.userpost.command.domain.service;

import com.runninghi.userpost.command.application.dto.response.UserPostUserResponse;

import java.util.UUID;

public interface ApiUserPostCommandDomainService {

    // 회원 찾기
    UserPostUserResponse checkUser(UUID userId);

}
