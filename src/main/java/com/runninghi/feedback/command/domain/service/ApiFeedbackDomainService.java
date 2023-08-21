package com.runninghi.feedback.command.domain.service;

import com.runninghi.feedback.command.application.dto.response.FeedbackUserResponse;

import java.util.UUID;

public interface ApiFeedbackDomainService {

    FeedbackUserResponse checkUser(UUID userId);

}
