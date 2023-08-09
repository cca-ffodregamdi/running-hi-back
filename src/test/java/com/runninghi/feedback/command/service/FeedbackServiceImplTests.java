package com.runninghi.feedback.command.service;

import com.runninghi.feedback.command.application.service.FeedbackServiceImpl;
import com.runninghi.feedback.command.domain.repository.FeedbackRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
public class FeedbackServiceImplTests {

    @Autowired
    private FeedbackServiceImpl feedbackServiceImpl;

    @Autowired
    private FeedbackRepository feedbackRepository;

//    @Test
//    @DisplayName("피드백 저장 테스트")
//    void saveFeedbackTest() {
//
//    }
}
