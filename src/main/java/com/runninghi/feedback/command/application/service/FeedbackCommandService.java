package com.runninghi.feedback.command.application.service;

import com.runninghi.common.handler.feedback.customException.NotFoundException;
import com.runninghi.common.handler.feedback.customException.NotMatchWriterException;
import com.runninghi.common.handler.feedback.customException.UnauthorizedAccessException;
import com.runninghi.feedback.command.application.dto.request.FeedbackCreateRequest;
import com.runninghi.feedback.command.application.dto.request.FeedbackDeleteRequest;
import com.runninghi.feedback.command.application.dto.request.FeedbackUpdateRequest;
import com.runninghi.feedback.command.application.dto.response.FeedbackResponse;
import com.runninghi.feedback.command.application.dto.response.FeedbackUserResponse;
import com.runninghi.feedback.command.domain.aggregate.entity.Feedback;
import com.runninghi.feedback.command.domain.aggregate.entity.FeedbackCategory;
import com.runninghi.feedback.command.domain.aggregate.vo.FeedbackWriterVO;
import com.runninghi.feedback.command.domain.repository.FeedbackCommandRepository;
import com.runninghi.feedback.command.domain.service.FeedbackCommandDomainService;
import com.runninghi.user.command.domain.repository.UserRepository;
import com.runninghi.feedback.command.domain.repository.FeedbackRepository;
import com.runninghi.user.command.domain.aggregate.entity.User;
import com.runninghi.user.command.domain.aggregate.entity.enumtype.Role;
import com.runninghi.user.command.domain.repository.UserCommandRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
@RequiredArgsConstructor
public class FeedbackCommandService {

    private final FeedbackCommandRepository feedbackCommandRepository;

    private final FeedbackCommandDomainService feedbackCommandDomainService;

    private final UserCommandRepository userRepository;



    // 피드백 저장
    @Transactional
    public FeedbackResponse createFeedback(FeedbackCreateRequest feedbackCreateRequest, FeedbackUserResponse user) {

        // 피드백 제한사항 확인(제목, 내용 길이)
        feedbackCommandDomainService.checkFeedbackValidation(feedbackCreateRequest.feedbackTitle(), feedbackCreateRequest.feedbackContent());

        // 작성자 vo 생성
        FeedbackWriterVO feedbackWriterVO = new FeedbackWriterVO(user.id());

        Feedback feedback = new Feedback.Builder()
                .feedbackTitle(feedbackCreateRequest.feedbackTitle())
                .feedbackContent(feedbackCreateRequest.feedbackContent())
                .feedbackWriterVO(feedbackWriterVO)
                .feedbackCategory(FeedbackCategory.fromValue(feedbackCreateRequest.feedbackCategory()))
                .build();

        // 피드백 저장
        Feedback savedFeedback = feedbackCommandRepository.save(feedback);

        return FeedbackResponse.from(savedFeedback);

    }

    // 피드백 수정
    @Transactional
    public FeedbackResponse updateFeedback(FeedbackUpdateRequest feedbackUpdateRequest, FeedbackUserResponse user) {

        Feedback feedback = feedbackCommandRepository.findByFeedbackNo(feedbackUpdateRequest.feedbackNo())
                .orElseThrow(() -> new NotFoundException("존재하지않는 피드백입니다."));

        // 작성자와 현재 회원이 일치하는지 확인
        if (!feedbackCommandDomainService.isWriter(user, feedback))
            throw new NotMatchWriterException("작성자와 회원이 일치하지않습니다.");

        // 피드백 제한사항 확인(제목, 내용 길이)
        feedbackCommandDomainService.checkFeedbackValidation(feedbackUpdateRequest.feedbackTitle(), feedbackUpdateRequest.feedbackContent());

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

        Feedback result = feedbackCommandRepository.save(updateFeedback);

        return FeedbackResponse.from(result);

    }

    // 피드백 삭제
    @Transactional
    public void deleteFeedback(FeedbackDeleteRequest feedbackDeleteRequest, FeedbackUserResponse user) {

        Feedback feedback = feedbackCommandRepository.findByFeedbackNo(feedbackDeleteRequest.feedbackNo())
                .orElseThrow(() -> new NotFoundException("존재하지않는 피드백입니다."));

        // 작성자와 현재 회원이 일치하는지 확인
        if (!feedbackCommandDomainService.isWriter(user, feedback))
            throw new NotMatchWriterException("작성자와 회원이 일치하지않습니다.");

        feedbackCommandRepository.delete(feedback);

    }

    // 관리자가 피드백 삭제
    @Transactional
    public void deleteFeedbackByAdmin(FeedbackDeleteRequest feedbackDeleteRequest, FeedbackUserResponse user) {

        Feedback feedback = feedbackCommandRepository.findByFeedbackNo(feedbackDeleteRequest.feedbackNo())
                .orElseThrow(() -> new NotFoundException("존재하지않는 피드백입니다."));

        // 작성자와 현재 회원이 일치하는지 확인
        if (!feedbackCommandDomainService.isAdminRole(user))
            throw new UnauthorizedAccessException("관리자가 아닙니다.");

        feedbackCommandRepository.delete(feedback);

    }

}
