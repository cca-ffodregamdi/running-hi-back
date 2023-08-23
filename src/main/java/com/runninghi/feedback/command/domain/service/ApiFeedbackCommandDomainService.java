package com.runninghi.feedback.command.domain.service;

import com.runninghi.feedback.command.application.dto.response.FeedbackUserResponse;

import java.util.UUID;

public interface ApiFeedbackCommandDomainService {

    // 회원 찾기
    FeedbackUserResponse checkUser(UUID userId);

}
