package com.runninghi.feedback.command.application.service;

import com.runninghi.feedback.command.application.dto.request.FeedbackReplyCreateRequest;
import com.runninghi.feedback.command.application.dto.request.FeedbackReplyDeleteRequest;
import com.runninghi.feedback.command.application.dto.request.FeedbackReplyUpdateRequest;
import com.runninghi.feedback.command.application.dto.response.FeedbackResponse;
import com.runninghi.feedback.command.domain.aggregate.entity.Feedback;
import com.runninghi.feedback.command.domain.exception.customException.NotFoundException;
import com.runninghi.feedback.command.domain.repository.FeedbackRepository;
import com.runninghi.feedback.command.domain.service.FeedbackReplyService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class FeedbackReplyServiceImpl implements FeedbackReplyService {

    private final FeedbackRepository feedbackRepository;

    // 피드백 답변 저장
    @Override
    @Transactional
    public FeedbackResponse createFeedbackReply(FeedbackReplyCreateRequest feedbackReplyCreateRequest) {

        Feedback feedback = feedbackRepository.findByFeedbackNo(feedbackReplyCreateRequest.feedbackNo())
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

        Feedback result = feedbackRepository.save(updateFeedback);

        return FeedbackResponse.from(result);

    }

    // 피드백 답변 삭제
    @Override
    @Transactional
    public FeedbackResponse deleteFeedbackReply(FeedbackReplyDeleteRequest feedbackReplyDeleteRequest) {

        Feedback feedback = feedbackRepository.findByFeedbackNo(feedbackReplyDeleteRequest.feedbackNo())
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

        Feedback result = feedbackRepository.save(updateFeedback);

        return FeedbackResponse.from(result);

    }

    // 피드백 답변 수정
    @Override
    @Transactional
    public FeedbackResponse updateFeedbackReply(FeedbackReplyUpdateRequest feedbackReplyUpdateRequest) {

        Feedback feedback = feedbackRepository.findByFeedbackNo(feedbackReplyUpdateRequest.feedbackNo())
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

        Feedback result = feedbackRepository.save(updateFeedback);

        return FeedbackResponse.from(result);

    }

}
