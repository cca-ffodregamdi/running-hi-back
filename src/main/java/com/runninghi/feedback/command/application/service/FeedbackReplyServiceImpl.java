package com.runninghi.feedback.command.application.service;

import com.runninghi.feedback.command.application.dto.FeedbackNoDTO;
import com.runninghi.feedback.command.application.dto.FeedbackReplyDTO;
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
    public Long saveFeedbackReply(FeedbackReplyDTO feedbackReplyDTO) {

        Feedback feedback = feedbackRepository.findByFeedbackNo(feedbackReplyDTO.getFeedbackNo())
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
                .feedbackReply(feedbackReplyDTO.getFeedbackReply())
                .feedbackReplyDate(new Date())
                .build();

        feedbackRepository.save(updateFeedback);

        return updateFeedback.getFeedbackNo();

    }

    // 피드백 답변 삭제
    @Override
    @Transactional
    public Long deleteFeedbackReply(FeedbackNoDTO feedbackNoDTO) {

        Feedback feedback = feedbackRepository.findByFeedbackNo(feedbackNoDTO.getFeedbackNo())
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

        feedbackRepository.save(updateFeedback);

        return updateFeedback.getFeedbackNo();

    }

    // 피드백 답변 수정
    @Override
    @Transactional
    public Long updateFeedbackReply(FeedbackReplyDTO feedbackReplyDTO) {

        Feedback feedback = feedbackRepository.findByFeedbackNo(feedbackReplyDTO.getFeedbackNo())
                .orElseThrow(() -> new NotFoundException("존재하지않는 피드백입니다."));

        Feedback updateFeedback = new Feedback.Builder()
                .feedbackNo(feedback.getFeedbackNo())
                .feedbackTitle(feedback.getFeedbackTitle())
                .feedbackContent(feedback.getFeedbackContent())
                .feedbackDate(feedback.getFeedbackDate())
                .feedbackWriterVO(feedback.getFeedbackWriterVO())
                .feedbackCategory(feedback.getFeedbackCategory())
                .feedbackStatus(true)
                .feedbackReply(feedbackReplyDTO.getFeedbackReply())
                .feedbackReplyDate(new Date())
                .build();

        feedbackRepository.save(updateFeedback);

        return updateFeedback.getFeedbackNo();

    }

}
