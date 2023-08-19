package com.runninghi.feedback.command.domain.service;

import com.runninghi.feedback.command.application.dto.request.FeedbackReplyDeleteRequest;
import com.runninghi.feedback.command.application.dto.request.FeedbackReplyCreateRequest;
import com.runninghi.feedback.command.application.dto.request.FeedbackReplyUpdateRequest;
import com.runninghi.feedback.command.application.dto.response.FeedbackResponse;
import jakarta.transaction.Transactional;

public interface FeedbackReplyService {

    // 피드백 답변 저장
    @Transactional
    FeedbackResponse createFeedbackReply(FeedbackReplyCreateRequest feedbackReplyCreateRequest);

    // 피드백 답변 삭제
    @Transactional
    FeedbackResponse deleteFeedbackReply(FeedbackReplyDeleteRequest feedbackReplyDeleteRequest);

    // 피드백 답변 수정
    @Transactional
    FeedbackResponse updateFeedbackReply(FeedbackReplyUpdateRequest feedbackReplyUpdateRequest);
}
