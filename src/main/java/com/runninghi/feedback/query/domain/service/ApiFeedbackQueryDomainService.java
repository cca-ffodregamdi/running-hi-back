package com.runninghi.feedback.query.domain.service;



import com.runninghi.feedback.query.application.dto.response.FeedbackUserResponse;

import java.util.UUID;

public interface ApiFeedbackQueryDomainService {

    FeedbackUserResponse checkUser(UUID userId);

}
