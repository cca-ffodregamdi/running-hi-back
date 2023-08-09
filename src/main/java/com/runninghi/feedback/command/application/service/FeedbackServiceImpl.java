package com.runninghi.feedback.command.application.service;

import com.runninghi.feedback.command.application.dto.SaveFeedbackDTO;
import com.runninghi.feedback.command.domain.exception.customException.NotFoundException;
import com.runninghi.feedback.command.domain.repository.FeedbackRepository;
import com.runninghi.feedback.command.domain.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;

    // 피드백 저장
    @Transactional
    public void saveFeedback(SaveFeedbackDTO feedbackDTO, Long userNo) {
//        User user = userRepository.findById(userNo)
//                .orElseThrow(() -> new NotFoundException("존재하지않는 회원입니다."));
    }

    // 피드백 삭제

    // 하나의 피드백 조회

    // 피드백 전체 조회

    // 피드백 답변 저장

    // 피드백 답변 삭제

    // 특정 카테고리의 피드백 전체 조회

    // 유저 본인이 작성한 피드백 전체 조회

}