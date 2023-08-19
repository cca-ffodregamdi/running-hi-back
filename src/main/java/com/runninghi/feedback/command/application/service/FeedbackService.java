package com.runninghi.feedback.command.application.service;

import com.runninghi.feedback.command.application.dto.request.FeedbackCreateRequest;
import com.runninghi.feedback.command.application.dto.response.FeedbackResponse;
import com.runninghi.feedback.command.domain.aggregate.entity.Feedback;
import com.runninghi.feedback.command.domain.aggregate.entity.FeedbackCategory;
import com.runninghi.feedback.command.domain.aggregate.vo.FeedbackWriterVO;
import com.runninghi.feedback.command.domain.exception.customException.IllegalArgumentException;
import com.runninghi.feedback.command.domain.exception.customException.NotFoundException;
import com.runninghi.feedback.command.domain.repository.FeedbackRepository;
import com.runninghi.user.command.domain.aggregate.entity.User;
import com.runninghi.user.command.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;

    // 피드백 저장
    @Transactional
    public FeedbackResponse createFeedback(FeedbackCreateRequest feedbackCreateRequest, UUID userId) {

        User writer = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("존재하지않는 회원입니다."));

        if (feedbackCreateRequest.feedbackTitle().length() > 500) {
            throw new IllegalArgumentException("제목은 500자를 넘을 수 없습니다.");
        }

        if (feedbackCreateRequest.feedbackTitle().length() == 0) {
            throw new IllegalArgumentException("제목은 1글자 이상이어야 합니다.");
        }

        if (feedbackCreateRequest.feedbackContent().length() == 0) {
            throw new IllegalArgumentException("내용은 1글자 이상이어야 합니다.");
        }

        // 작성자 vo 생성
        FeedbackWriterVO feedbackWriterVO = new FeedbackWriterVO(userId);

        Feedback feedback = new Feedback.Builder()
                .feedbackTitle(feedbackCreateRequest.feedbackTitle())
                .feedbackContent(feedbackCreateRequest.feedbackContent())
                .feedbackWriterVO(feedbackWriterVO)
                .feedbackCategory(FeedbackCategory.fromValue(feedbackCreateRequest.feedbackCategory()))
                .build();

        // 피드백 저장
        Feedback savedFeedback = feedbackRepository.save(feedback);

        return FeedbackResponse.from(savedFeedback);

    }

}