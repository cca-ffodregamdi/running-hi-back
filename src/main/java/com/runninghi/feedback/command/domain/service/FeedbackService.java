package com.runninghi.feedback.command.domain.service;

import com.runninghi.feedback.command.application.dto.FeedbackNoDTO;
import com.runninghi.feedback.command.application.dto.FeedbackReplyDTO;
import com.runninghi.feedback.command.application.dto.SaveFeedbackDTO;
import jakarta.transaction.Transactional;

public interface FeedbackService {

    // 피드백 저장
    @Transactional
    Long saveFeedback(SaveFeedbackDTO feedbackDTO, Long userNo);

    @Transactional
    Long saveFeedbackReply(FeedbackReplyDTO feedbackReplyDTO);

    // 피드백 답변 삭제
    @Transactional
    Long deleteFeedbackReply(FeedbackNoDTO feedbackNoDTO);
}
