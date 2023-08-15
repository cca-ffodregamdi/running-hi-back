package com.runninghi.feedback.command.domain.service;

import com.runninghi.feedback.command.application.dto.SaveFeedbackDTO;
import jakarta.transaction.Transactional;

import java.util.UUID;

public interface FeedbackService {

    // 피드백 저장
    @Transactional
    Long saveFeedback(SaveFeedbackDTO feedbackDTO, UUID userID);

}
