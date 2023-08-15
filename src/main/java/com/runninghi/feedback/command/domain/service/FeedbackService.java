package com.runninghi.feedback.command.domain.service;

import com.runninghi.feedback.command.application.dto.SaveFeedbackDTO;
import jakarta.transaction.Transactional;

public interface FeedbackService {

    // 피드백 저장
    @Transactional
    Long saveFeedback(SaveFeedbackDTO feedbackDTO, Long userNo);

}
