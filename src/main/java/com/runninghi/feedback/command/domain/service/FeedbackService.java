package com.runninghi.feedback.command.domain.service;

import com.runninghi.feedback.command.application.dto.DeleteFeedbackDTO;
import com.runninghi.feedback.command.application.dto.FeedbackReplyDTO;
import com.runninghi.feedback.command.application.dto.SaveFeedbackDTO;

import javax.transaction.Transactional;


public interface FeedbackService {

    // 피드백 저장
    @Transactional
    Long saveFeedback(SaveFeedbackDTO feedbackDTO, Long userNo);

}
