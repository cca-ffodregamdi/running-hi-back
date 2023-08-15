package com.runninghi.feedback.command.domain.service;

import com.runninghi.feedback.command.application.dto.FeedbackNoDTO;
import com.runninghi.feedback.command.application.dto.FeedbackReplyDTO;
import jakarta.transaction.Transactional;

public interface FeedbackReplyService {

    // 피드백 답변 저장
    @Transactional
    Long saveFeedbackReply(FeedbackReplyDTO feedbackReplyDTO);

    // 피드백 답변 삭제
    @Transactional
    Long deleteFeedbackReply(FeedbackNoDTO feedbackNoDTO);

    // 피드백 답변 수정
    @Transactional
    Long modifyFeedbackReply(FeedbackReplyDTO feedbackReplyDTO);
}
