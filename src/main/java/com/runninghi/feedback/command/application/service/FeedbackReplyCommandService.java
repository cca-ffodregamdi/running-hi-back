package com.runninghi.feedback.command.application.service;

import com.runninghi.common.handler.feedback.customException.NotFoundException;
import com.runninghi.feedback.command.application.dto.request.FeedbackReplyCreateRequest;
import com.runninghi.feedback.command.application.dto.request.FeedbackReplyDeleteRequest;
import com.runninghi.feedback.command.application.dto.request.FeedbackReplyUpdateRequest;
import com.runninghi.feedback.command.application.dto.response.FeedbackResponse;
import com.runninghi.feedback.command.domain.aggregate.entity.Feedback;
import com.runninghi.feedback.command.domain.repository.FeedbackCommandRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class FeedbackReplyCommandService {

    private final FeedbackCommandRepository feedbackCommandRepository;

    // 피드백 답변 저장
    @Transactional
    public FeedbackResponse createFeedbackReply(FeedbackReplyCreateRequest feedbackReplyCreateRequest) {

        Feedback feedback = feedbackCommandRepository.findByFeedbackNo(feedbackReplyCreateRequest.feedbackNo())
                .orElseThrow(() -> new NotFoundException("존재하지않는 피드백입니다."));

        Feedback updateFeedback = new Feedback.Builder()
                .feedbackNo(feedback.getFeedbackNo())
                .feedbackNo(feedback.getFeedbackNo())
                .feedbackTitle(feedback.getFeedbackTitle())
                .feedbackContent(feedback.getFeedbackContent())
                .feedbackDate(feedback.getFeedbackDate())
                .feedbackWriterVO(feedback.getFeedbackWriterVO())
                .feedbackCategory(feedback.getFeedbackCategory())
                .feedbackStatus(true)
                .feedbackReply(feedbackReplyCreateRequest.feedbackReply())
                .feedbackReplyDate(new Date())
                .build();

        Feedback result = feedbackCommandRepository.save(updateFeedback);

        return FeedbackResponse.from(result);

    }

    // 피드백 답변 삭제
    @Transactional
    public FeedbackResponse deleteFeedbackReply(FeedbackReplyDeleteRequest feedbackReplyDeleteRequest) {

        Feedback feedback = feedbackCommandRepository.findByFeedbackNo(feedbackReplyDeleteRequest.feedbackNo())
                .orElseThrow(() -> new NotFoundException("존재하지않는 피드백입니다."));

        Feedback updateFeedback = new Feedback.Builder()
                .feedbackNo(feedback.getFeedbackNo())
                .feedbackNo(feedback.getFeedbackNo())
                .feedbackTitle(feedback.getFeedbackTitle())
                .feedbackContent(feedback.getFeedbackContent())
                .feedbackDate(feedback.getFeedbackDate())
                .feedbackCategory(feedback.getFeedbackCategory())
                .feedbackWriterVO(feedback.getFeedbackWriterVO())
                .feedbackStatus(false)
                .feedbackReply(null)
                .feedbackReplyDate(null)
                .build();

        Feedback result = feedbackCommandRepository.save(updateFeedback);

        return FeedbackResponse.from(result);

    }

    // 피드백 답변 수정
    @Transactional
    public FeedbackResponse updateFeedbackReply(FeedbackReplyUpdateRequest feedbackReplyUpdateRequest) {

        Feedback feedback = feedbackCommandRepository.findByFeedbackNo(feedbackReplyUpdateRequest.feedbackNo())
                .orElseThrow(() -> new NotFoundException("존재하지않는 피드백입니다."));

        Feedback updateFeedback = new Feedback.Builder()
                .feedbackNo(feedback.getFeedbackNo())
                .feedbackTitle(feedback.getFeedbackTitle())
                .feedbackContent(feedback.getFeedbackContent())
                .feedbackDate(feedback.getFeedbackDate())
                .feedbackWriterVO(feedback.getFeedbackWriterVO())
                .feedbackCategory(feedback.getFeedbackCategory())
                .feedbackStatus(true)
                .feedbackReply(feedbackReplyUpdateRequest.feedbackReply())
                .feedbackReplyDate(new Date())
                .build();

        Feedback result = feedbackCommandRepository.save(updateFeedback);

        return FeedbackResponse.from(result);

    }

}
