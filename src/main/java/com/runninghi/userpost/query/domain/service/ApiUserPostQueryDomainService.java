package com.runninghi.userpost.query.domain.service;

import com.runninghi.userpost.query.application.dto.response.UserPostUserResponse;

import java.util.UUID;

public interface ApiUserPostQueryDomainService {

    // 회원 찾기
    UserPostUserResponse checkUser(UUID userId);

}
