package com.runninghi.feedback.command.application.service;

import com.runninghi.common.handler.feedback.customException.IllegalArgumentException;
import com.runninghi.common.handler.feedback.customException.NotFoundException;
import com.runninghi.common.handler.feedback.customException.NotMatchWriterException;
import com.runninghi.common.handler.feedback.customException.UnauthorizedAccessException;
import com.runninghi.feedback.command.application.dto.request.FeedbackCreateRequest;
import com.runninghi.feedback.command.application.dto.request.FeedbackDeleteRequest;
import com.runninghi.feedback.command.application.dto.request.FeedbackUpdateRequest;
import com.runninghi.feedback.command.application.dto.response.FeedbackResponse;
import com.runninghi.feedback.command.domain.aggregate.entity.Feedback;
import com.runninghi.feedback.command.domain.aggregate.entity.FeedbackCategory;
import com.runninghi.feedback.command.domain.aggregate.vo.FeedbackWriterVO;
import com.runninghi.feedback.command.domain.repository.FeedbackRepository;
import com.runninghi.user.command.domain.aggregate.entity.User;
import com.runninghi.user.command.domain.aggregate.entity.enumtype.Role;
import com.runninghi.user.command.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class FeedbackCommandService {

    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;

    // 피드백 저장
    @Transactional
    public FeedbackResponse createFeedback(FeedbackCreateRequest feedbackCreateRequest, UUID userId) {

        User writer = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("존재하지않는 회원입니다."));

        // 피드백 제한사항 확인(제목, 내용 길이)
        checkFeedbackValidation(feedbackCreateRequest.feedbackTitle(), feedbackCreateRequest.feedbackContent());

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

    // 피드백 수정
    @Transactional
    public FeedbackResponse updateFeedback(FeedbackUpdateRequest feedbackUpdateRequest, UUID userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("존재하지않는 회원입니다."));

        Feedback feedback = feedbackRepository.findByFeedbackNo(feedbackUpdateRequest.feedbackNo())
                .orElseThrow(() -> new NotFoundException("존재하지않는 피드백입니다."));

        // 작성자와 현재 회원이 일치하는지 확인
        if (!isWriter(userId, feedback))
            throw new NotMatchWriterException("작성자와 회원이 일치하지않습니다.");

        // 피드백 제한사항 확인(제목, 내용 길이)
        checkFeedbackValidation(feedbackUpdateRequest.feedbackTitle(), feedbackUpdateRequest.feedbackContent());

        FeedbackCategory feedbackCategory = FeedbackCategory.fromValue(feedbackUpdateRequest.feedbackCategory());

        Feedback updateFeedback = new Feedback.Builder()
                .feedbackNo(feedback.getFeedbackNo())
                .feedbackTitle(feedbackUpdateRequest.feedbackTitle())
                .feedbackContent(feedbackUpdateRequest.feedbackContent())
                .feedbackDate(new Date())
                .feedbackWriterVO(feedback.getFeedbackWriterVO())
                .feedbackCategory(feedbackCategory)
                .feedbackStatus(feedback.getFeedbackStatus())
                .feedbackReply(feedback.getFeedbackReply())
                .feedbackReplyDate(feedback.getFeedbackReplyDate())
                .build();

        Feedback result = feedbackRepository.save(updateFeedback);

        return FeedbackResponse.from(result);

    }

    // 피드백 삭제
    @Transactional
    public void deleteFeedback(FeedbackDeleteRequest feedbackDeleteRequest, UUID userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("존재하지않는 회원입니다."));

        Feedback feedback = feedbackRepository.findByFeedbackNo(feedbackDeleteRequest.feedbackNo())
                .orElseThrow(() -> new NotFoundException("존재하지않는 피드백입니다."));

        // 작성자와 현재 회원이 일치하는지 확인
        if (!isWriter(userId, feedback))
            throw new NotMatchWriterException("작성자와 회원이 일치하지않습니다.");

        feedbackRepository.delete(feedback);

    }

    // 관리자가 피드백 삭제
    @Transactional
    public void deleteFeedbackByAdmin(FeedbackDeleteRequest feedbackDeleteRequest, UUID userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("존재하지않는 회원입니다."));

        Feedback feedback = feedbackRepository.findByFeedbackNo(feedbackDeleteRequest.feedbackNo())
                .orElseThrow(() -> new NotFoundException("존재하지않는 피드백입니다."));

        // 작성자와 현재 회원이 일치하는지 확인
        if (!isAdminRole(user))
            throw new UnauthorizedAccessException("관리자가 아닙니다.");

        feedbackRepository.delete(feedback);

    }

    // 피드백 작성 시 제한 사항 확인
    private void checkFeedbackValidation(String title, String content) {

        if (title.length() > 500) {
            throw new IllegalArgumentException("제목은 500자를 넘을 수 없습니다.");
        }

        if (title.length() == 0) {
            throw new IllegalArgumentException("제목은 1글자 이상이어야 합니다.");
        }

        if (content.length() == 0) {
            throw new IllegalArgumentException("내용은 1글자 이상이어야 합니다.");
        }

    }

    // 피드백 수정/삭제 요청자와 피드백 작성자가 동일한지 확인
    private boolean isWriter(UUID userId, Feedback feedback) {

        return userId.equals(feedback.getFeedbackWriterVO().getFeedbackWriterId());

    }

    // 요청자가 관리자인지 확인
    private boolean isAdminRole(User user) {

        return user.getRole().equals(Role.ADMIN);

    }

}
