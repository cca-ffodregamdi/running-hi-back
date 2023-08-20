package com.runninghi.feedback.query.application.service;

import com.runninghi.common.handler.feedback.customException.NotFoundException;
import com.runninghi.common.handler.feedback.customException.UnauthorizedAccessException;
import com.runninghi.feedback.command.domain.repository.FeedbackRepository;
import com.runninghi.feedback.query.application.dto.request.FeedbackFindRequest;
import com.runninghi.feedback.query.application.dto.response.FeedbackFindResponse;
import com.runninghi.feedback.command.application.dto.response.FeedbackResponse;
import com.runninghi.feedback.command.domain.aggregate.entity.Feedback;
import com.runninghi.feedback.command.domain.aggregate.entity.FeedbackCategory;
import com.runninghi.user.command.domain.aggregate.entity.User;
import com.runninghi.user.command.domain.aggregate.entity.enumtype.Role;
import com.runninghi.user.command.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FeedbackQueryService {

    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;

    // 피드백 조회 (본인, 관리자)
    @Transactional
    public FeedbackResponse findFeedback(FeedbackFindRequest feedbackFindRequest, UUID userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("존재하지않는 회원입니다."));

        Feedback feedback = feedbackRepository.findByFeedbackNo(feedbackFindRequest.feedbackNo())
                .orElseThrow(() -> new NotFoundException("존재하지않는 피드백입니다."));

        if (!isWriter(userId, feedback) && !isAdminRole(user)) {
            throw new UnauthorizedAccessException("피드백 조회 권한이 올바르지않습니다.");
        }

        return FeedbackResponse.from(feedback);

    }

    // 피드백 전체 조회 (카테고리로 필터링)
    @Transactional
    public Page<FeedbackFindResponse> findAllFeedback(FeedbackFindRequest feedbackFindRequest, Pageable pageable) {

        Page<Feedback> feedbackPage;

        if (feedbackFindRequest.feedbackCategory() == null) {
            // 필터링없이 전체 조회
            feedbackPage = feedbackRepository.findAllFeedback(pageable);
        }
        else {
            // 카테고리로 필터링할 때
            feedbackPage = feedbackRepository.findFeedbacksByFeedbackCategory(FeedbackCategory.fromValue(feedbackFindRequest.feedbackCategory()), pageable);
        }

        // 조회한 Page<Feedback>을 Page<FeedbackFindResponse>로 변환해서 반환
        return feedbackPage.map(feedback -> {
            User user = userRepository.findById(feedback.getFeedbackWriterVO().getFeedbackWriterId()).orElse(null);
            String nickname = user != null ? user.getNickname() : null;

            return FeedbackFindResponse.from(feedback, nickname);
        });

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
