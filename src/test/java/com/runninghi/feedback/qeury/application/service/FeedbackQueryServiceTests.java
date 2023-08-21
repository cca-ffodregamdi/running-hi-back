package com.runninghi.feedback.qeury.application.service;

import com.runninghi.feedback.command.domain.aggregate.entity.Feedback;
import com.runninghi.feedback.command.domain.aggregate.entity.FeedbackCategory;
import com.runninghi.feedback.command.domain.aggregate.vo.FeedbackWriterVO;
import com.runninghi.feedback.command.domain.repository.FeedbackRepository;
import com.runninghi.feedback.query.application.dto.request.FeedbackFindRequest;
import com.runninghi.feedback.query.application.dto.response.FeedbackFindResponse;
import com.runninghi.feedback.query.application.service.FeedbackQueryService;
import com.runninghi.user.command.domain.aggregate.entity.User;
import com.runninghi.user.command.domain.aggregate.entity.enumtype.Role;
import com.runninghi.user.command.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@SpringBootTest
@Transactional
public class FeedbackQueryServiceTests {

    @Autowired
    private FeedbackQueryService feedbackQueryService;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    private User user1;

    private User user2;

    private Feedback setUpFeedback1;

    private Feedback setUpFeedback2;

    @BeforeEach
    @AfterEach
    void clear() {
        userRepository.deleteAll();
        feedbackRepository.deleteAll();
    }

    @BeforeEach
    public void setUp() {

        user1 = userRepository.save(User.builder()
                .account("qwerty1234")
                .password(encoder.encode("1234"))
                .name("qweqwe")
                .nickname("닉네임입니다")
                .role(Role.USER)
                .build());

        user2 = userRepository.save(User.builder()
                .account("testUser")
                .password(encoder.encode("1111"))
                .name("testUUUUser")
                .nickname("user2닉네임입니다")
                .role(Role.USER)
                .build());

        FeedbackWriterVO feedbackWriterVO = new FeedbackWriterVO(user1.getId());
        FeedbackCategory feedbackCategory = FeedbackCategory.fromValue(1);

        Feedback feedback = new Feedback.Builder()
                .feedbackTitle("피드백 제목")
                .feedbackContent("피드백 내용")
                .feedbackDate(new Date())
                .feedbackWriterVO(feedbackWriterVO)
                .feedbackStatus(false)
                .feedbackReply(null)
                .feedbackReplyDate(null)
                .feedbackCategory(feedbackCategory)
                .build();

        setUpFeedback1 = feedbackRepository.save(feedback);

        FeedbackCategory feedbackCategory2 = FeedbackCategory.fromValue(2);

        Feedback feedback2 = new Feedback.Builder()
                .feedbackTitle("피드백 제목222222")
                .feedbackContent("피드백 내용2222222")
                .feedbackDate(new Date())
                .feedbackWriterVO(feedbackWriterVO)
                .feedbackStatus(false)
                .feedbackReply(null)
                .feedbackReplyDate(null)
                .feedbackCategory(feedbackCategory2)
                .build();

        setUpFeedback2 = feedbackRepository.save(feedback2);
    }

    @Test
    @DisplayName("피드백 조회 테스트 : 필터없이 조회 success")
    void checkFindFeedbackAllTest() {

        FeedbackFindRequest feedbackFindRequest = new FeedbackFindRequest(null, null);

        Page<FeedbackFindResponse> feedbackPage = feedbackQueryService.findAllFeedback(feedbackFindRequest, Pageable.unpaged());

        Assertions.assertEquals(feedbackRepository.count(), feedbackPage.getTotalElements());
    }

    @Test
    @DisplayName("피드백 조회 테스트 : 카테고리로 조회 success")
    void checkFindFeedbackWithCategoryTest() {

        FeedbackFindRequest feedbackFindRequest = new FeedbackFindRequest(null, 1);

        Page<FeedbackFindResponse> feedbackPage = feedbackQueryService.findAllFeedback(feedbackFindRequest, Pageable.unpaged());

        Assertions.assertEquals(1, feedbackPage.getTotalElements());
        Assertions.assertEquals(setUpFeedback1.getFeedbackTitle(), feedbackPage.getContent().get(0).feedbackTitle());

    }

}
