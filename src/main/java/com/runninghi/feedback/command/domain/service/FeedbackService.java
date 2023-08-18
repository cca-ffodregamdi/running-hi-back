package com.runninghi.feedback.command.domain.service;

import com.runninghi.feedback.command.application.dto.request.FeedbackCreateRequest;
import com.runninghi.feedback.command.application.dto.response.FeedbackResponse;
import jakarta.transaction.Transactional;

import java.util.UUID;

public interface FeedbackService {

    // 피드백 저장
    @Transactional
    FeedbackResponse createFeedback(FeedbackCreateRequest feedbackDTO, UUID userID);

}
