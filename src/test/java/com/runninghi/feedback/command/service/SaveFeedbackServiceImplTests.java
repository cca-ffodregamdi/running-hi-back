package com.runninghi.feedback.command.service;

import com.runninghi.feedback.command.application.dto.SaveFeedbackDTO;
import com.runninghi.feedback.command.application.service.FeedbackServiceImpl;
import com.runninghi.feedback.command.domain.exception.customException.IllegalArgumentException;
import com.runninghi.feedback.command.domain.repository.FeedbackRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@Transactional
public class SaveFeedbackServiceImplTests {

    @Autowired
    private FeedbackServiceImpl feedbackServiceImpl;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Test
    @DisplayName("피드백 저장 테스트 : success")
    void saveFeedbackTest() {
        long before = feedbackRepository.count();
        SaveFeedbackDTO saveFeedbackDTO = new SaveFeedbackDTO("제목", "내용", 0);

        feedbackServiceImpl.saveFeedback(saveFeedbackDTO, 0L);

        long after = feedbackRepository.count();

        Assertions.assertEquals(before + 1, after);
    }

    @Test
    @DisplayName("피드백 저장 테스트 : 제목 500자 제한 확인")
    void checkFeedbackTitleTest() {
        String str = "a".repeat(501);

        long before = feedbackRepository.count();
        SaveFeedbackDTO saveFeedbackDTO = new SaveFeedbackDTO(str, "내용", 0);

        Assertions.assertThrows(IllegalArgumentException.class, () -> feedbackServiceImpl.saveFeedback(saveFeedbackDTO, 0L));

        long after = feedbackRepository.count();
        Assertions.assertEquals(before, after);
    }

    @Test
    @DisplayName("피드백 저장 테스트 : 내용 null 제한 확인")
    void checkFeedbackContentTest() {
        String str = "";

        long before = feedbackRepository.count();
        SaveFeedbackDTO saveFeedbackDTO = new SaveFeedbackDTO("제목", str, 0);

        Assertions.assertThrows(IllegalArgumentException.class, () -> feedbackServiceImpl.saveFeedback(saveFeedbackDTO, 0L));

        long after = feedbackRepository.count();
        Assertions.assertEquals(before, after);
    }

    @Test
    @DisplayName("피드백 저장 테스트 : 카테고리 번호가 올바른지 확인")
    void checkFeedbackCategoryTest() throws InterruptedException {
        long before = feedbackRepository.count();

        SaveFeedbackDTO saveFeedbackDTO = new SaveFeedbackDTO("제목", "내용", 1000);
        Assertions.assertThrows(IllegalArgumentException.class, () -> feedbackServiceImpl.saveFeedback(saveFeedbackDTO, 0L));

        long after = feedbackRepository.count();
        Assertions.assertEquals(before, after);
    }
}