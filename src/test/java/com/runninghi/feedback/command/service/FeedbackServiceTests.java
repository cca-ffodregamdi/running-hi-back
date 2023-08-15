package com.runninghi.feedback.command.service;

import com.runninghi.feedback.command.application.dto.SaveFeedbackDTO;
import com.runninghi.feedback.command.domain.exception.customException.IllegalArgumentException;
import com.runninghi.feedback.command.domain.repository.FeedbackRepository;
import com.runninghi.feedback.command.domain.service.FeedbackService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
@Transactional
public class FeedbackServiceTests {

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Test
    @DisplayName("피드백 저장 테스트 : success")
    void saveFeedbackTest() {
        long before = feedbackRepository.count();
        SaveFeedbackDTO saveFeedbackDTO = new SaveFeedbackDTO("제목", "내용", 0);

        feedbackService.saveFeedback(saveFeedbackDTO, UUID.randomUUID());

        long after = feedbackRepository.count();

        Assertions.assertEquals(before + 1, after);
    }

    @Test
    @DisplayName("피드백 저장 테스트 : 제목 500자 제한 확인")
    void checkFeedbackTitleTest() {
        String str = "a".repeat(501);

        long before = feedbackRepository.count();
        SaveFeedbackDTO saveFeedbackDTO = new SaveFeedbackDTO(str, "내용", 0);

        Assertions.assertThrows(IllegalArgumentException.class, () -> feedbackService.saveFeedback(saveFeedbackDTO, UUID.randomUUID()));

        long after = feedbackRepository.count();
        Assertions.assertEquals(before, after);
    }

    @Test
    @DisplayName("피드백 저장 테스트 : 내용 null 제한 확인")
    void checkFeedbackContentTest() {
        String str = "";

        long before = feedbackRepository.count();
        SaveFeedbackDTO saveFeedbackDTO = new SaveFeedbackDTO("제목", str, 0);

        Assertions.assertThrows(IllegalArgumentException.class, () -> feedbackService.saveFeedback(saveFeedbackDTO, UUID.randomUUID()));

        long after = feedbackRepository.count();
        Assertions.assertEquals(before, after);
    }

    @Test
    @DisplayName("피드백 저장 테스트 : 카테고리 번호가 올바른지 확인")
    void checkFeedbackCategoryTest() {
        long before = feedbackRepository.count();

        SaveFeedbackDTO saveFeedbackDTO = new SaveFeedbackDTO("제목", "내용", 1000);
        Assertions.assertThrows(IllegalArgumentException.class, () -> feedbackService.saveFeedback(saveFeedbackDTO, UUID.randomUUID()));

        long after = feedbackRepository.count();
        Assertions.assertEquals(before, after);
    }
}