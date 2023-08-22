package com.runninghi.feedback.command.service;


import com.runninghi.common.handler.feedback.customException.IllegalArgumentException;
import com.runninghi.feedback.command.application.dto.request.FeedbackCreateRequest;
import com.runninghi.feedback.command.application.service.FeedbackCommandService;
import com.runninghi.feedback.command.domain.repository.FeedbackRepository;
import com.runninghi.user.command.domain.aggregate.entity.User;
import com.runninghi.user.command.domain.aggregate.entity.enumtype.Role;
import com.runninghi.user.command.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@Transactional
public class FeedbackCommandServiceTests {

    @Autowired
    private FeedbackCommandService feedbackService;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    private User user;

    @BeforeEach
    public void setUp() {

        user = userRepository.save(User.builder()
                .account("qwerty1234")
                .password(encoder.encode("1234"))
                .name("김철수")
                .nickname("qwe")
                .email("qwe@qwe.qw")
                .role(Role.USER)
                .status(true)
                .build());

    }

    @Test
    @DisplayName("피드백 저장 테스트 : success")
    void saveFeedbackTest() {

        long before = feedbackRepository.count();

        FeedbackCreateRequest feedbackCreateRequest = new FeedbackCreateRequest("제목", "내용", 0);

        feedbackService.createFeedback(feedbackCreateRequest, user.getId());

        long after = feedbackRepository.count();

        Assertions.assertEquals(before + 1, after);

    }

    @Test
    @DisplayName("피드백 저장 테스트 : 제목 500자 초과 시 예외처리")
    void checkFeedbackTitleTest() {

        String userId = "ac60fb25-b40b-4308-827a-5aba81860fcb";
        String str = "a".repeat(501);

        long before = feedbackRepository.count();
        FeedbackCreateRequest feedbackCreateRequest = new FeedbackCreateRequest(str, "내용", 0);

        Assertions.assertThrows(IllegalArgumentException.class, () -> feedbackService.createFeedback(feedbackCreateRequest, user.getId()));

        long after = feedbackRepository.count();
        Assertions.assertEquals(before, after);

    }

    @Test
    @DisplayName("피드백 저장 테스트 : 내용 null 일 시 예외처리")
    void checkFeedbackContentTest() {

        String userId = "ac60fb25-b40b-4308-827a-5aba81860fcb";
        String str = "";

        long before = feedbackRepository.count();
        FeedbackCreateRequest feedbackCreateRequest = new FeedbackCreateRequest("제목", str, 0);

        Assertions.assertThrows(IllegalArgumentException.class, () -> feedbackService.createFeedback(feedbackCreateRequest, user.getId()));

        long after = feedbackRepository.count();
        Assertions.assertEquals(before, after);

    }

    @Test
    @DisplayName("피드백 저장 테스트 : 카테고리 번호가 틀리면 예외처리")
    void checkFeedbackCategoryTest() {

        String userId = "ac60fb25-b40b-4308-827a-5aba81860fcb";
        long before = feedbackRepository.count();

        FeedbackCreateRequest feedbackCreateRequest = new FeedbackCreateRequest("제목", "내용", 1000);
        Assertions.assertThrows(IllegalArgumentException.class, () -> feedbackService.createFeedback(feedbackCreateRequest, user.getId()));

        long after = feedbackRepository.count();
        Assertions.assertEquals(before, after);
    }
}