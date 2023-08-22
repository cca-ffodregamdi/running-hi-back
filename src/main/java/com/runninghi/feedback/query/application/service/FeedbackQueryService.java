package com.runninghi.feedback.query.application.service;

import com.runninghi.common.handler.feedback.customException.NotFoundException;
import com.runninghi.common.handler.feedback.customException.UnauthorizedAccessException;
import com.runninghi.feedback.command.application.dto.response.FeedbackResponse;
import com.runninghi.feedback.command.domain.aggregate.entity.Feedback;
import com.runninghi.feedback.command.domain.aggregate.entity.FeedbackCategory;
import com.runninghi.feedback.command.domain.aggregate.vo.FeedbackWriterVO;
import com.runninghi.feedback.query.application.dto.request.FeedbackFindRequest;
import com.runninghi.feedback.query.application.dto.request.FeedbackStatusRequest;
import com.runninghi.feedback.query.application.dto.response.FeedbackFindResponse;
import com.runninghi.feedback.query.application.dto.response.FeedbackStatusResponse;
import com.runninghi.feedback.query.application.dto.response.FeedbackUserResponse;
import com.runninghi.feedback.query.domain.repository.FeedbackQueryRepository;
import com.runninghi.feedback.query.domain.service.ApiFeedbackQueryDomainService;
import com.runninghi.feedback.query.domain.service.FeedbackQueryDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FeedbackQueryService {

    private final FeedbackQueryRepository feedbackQueryRepository;

    private final FeedbackQueryDomainService feedbackQueryDomainService;

    private final ApiFeedbackQueryDomainService apiFeedbackQueryDomainService;

    // 피드백 조회 (본인, 관리자)
    @Transactional(readOnly = true)
    public FeedbackResponse findFeedback(FeedbackFindRequest feedbackFindRequest, FeedbackUserResponse user) {

        Feedback feedback = feedbackQueryRepository.findByFeedbackNo(feedbackFindRequest.feedbackNo())
                .orElseThrow(() -> new NotFoundException("존재하지않는 피드백입니다."));

        if (!feedbackQueryDomainService.isWriter(user, feedback) && !feedbackQueryDomainService.isAdminRole(user)) {
            throw new UnauthorizedAccessException("피드백 조회 권한이 올바르지않습니다.");
        }

        return FeedbackResponse.from(feedback);

    }

    // 피드백 전체 조회 (카테고리로 필터링)
    @Transactional(readOnly = true)
    public Page<FeedbackFindResponse> findAllFeedback(FeedbackFindRequest feedbackFindRequest, Pageable pageable) {

        Page<Feedback> feedbackPage;

        if (feedbackFindRequest.feedbackCategory() == null) {
            // 필터링없이 전체 조회
            feedbackPage = feedbackQueryRepository.findAll(pageable);
        }
        else {
            // 카테고리로 필터링할 때
            feedbackPage = feedbackQueryRepository.findFeedbacksByFeedbackCategory(FeedbackCategory.fromValue(feedbackFindRequest.feedbackCategory()), pageable);
        }

        // 조회한 Page<Feedback>을 Page<FeedbackFindResponse>로 변환해서 반환
        return feedbackPage.map(feedback -> {
            FeedbackUserResponse user = apiFeedbackQueryDomainService.checkUser(feedback.getFeedbackWriterVO().getFeedbackWriterId());
            String nickname = user.nickname() != null ? user.nickname() : null;

            return FeedbackFindResponse.from(feedback, nickname);
        });

    }

    // 답변 여부로 필터링해서 피드백 전체 조회
    @Transactional(readOnly = true)
    public Page<FeedbackStatusResponse> findFeedbackbyReplyStatus(FeedbackStatusRequest feedbackStatusRequest, Pageable pageable) {

        Page<Feedback> feedbackPage = feedbackQueryRepository.findFeedbacksByFeedbackStatus(feedbackStatusRequest.feedbackStatus(), pageable);

        // 조회한 Page<Feedback>을 Page<FeedbackFindResponse>로 변환해서 반환
        return feedbackPage.map(feedback -> {
            FeedbackUserResponse user = apiFeedbackQueryDomainService.checkUser(feedback.getFeedbackWriterVO().getFeedbackWriterId());
            String nickname = user.nickname() != null ? user.nickname() : null;

            return FeedbackStatusResponse.from(feedback, nickname);
        });
    }


    // 본인의 피드백 전체 조회
    @Transactional(readOnly = true)
    public Page<Feedback> findFeedbackMyPage(FeedbackUserResponse feedbackUserResponse, Pageable pageable) {

        FeedbackWriterVO feedbackWriterVO = new FeedbackWriterVO(feedbackUserResponse.id());

        return feedbackQueryRepository.findFeedbacksByFeedbackWriterVO(feedbackWriterVO, pageable);
    }
}
