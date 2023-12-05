package com.runninghi.feedback.command.service;


import com.runninghi.common.handler.feedback.customException.IllegalArgumentException;
import com.runninghi.feedback.command.application.dto.request.FeedbackCreateRequest;
import com.runninghi.feedback.command.application.dto.request.FeedbackDeleteRequest;
import com.runninghi.feedback.command.application.dto.request.FeedbackUpdateRequest;
import com.runninghi.feedback.command.application.dto.response.FeedbackUserResponse;
import com.runninghi.feedback.command.application.service.FeedbackCommandService;
import com.runninghi.feedback.command.domain.aggregate.entity.Feedback;
import com.runninghi.feedback.command.domain.aggregate.entity.FeedbackCategory;
import com.runninghi.feedback.command.domain.aggregate.vo.FeedbackWriterVO;
import com.runninghi.feedback.command.domain.repository.FeedbackCommandRepository;
import com.runninghi.feedback.command.infrastructure.service.ApiFeedbackCommandInfraService;
import com.runninghi.member.command.domain.aggregate.entity.Member;
import com.runninghi.member.command.domain.aggregate.entity.enumtype.Role;
import com.runninghi.member.command.domain.repository.MemberCommandRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@SpringBootTest
@Transactional
public class FeedbackCommandServiceTests {

    @Autowired
    private ApiFeedbackCommandInfraService apiFeedbackCommandInfraService;

    @Autowired
    private FeedbackCommandService feedbackCommandService;

    @Autowired
    private FeedbackCommandRepository feedbackCommandRepository;

    @Autowired
    private MemberCommandRepository userCommandRepository;

    @Autowired
    private PasswordEncoder encoder;

    private Member user1;

    private Member user2;

    private Feedback setUpFeedback1;

    private Feedback setUpFeedback2;

    @BeforeEach
    @AfterEach
    void clear() {
        userCommandRepository.deleteAllInBatch();
        feedbackCommandRepository.deleteAllInBatch();
    }

    @BeforeEach
    public void setUp() {

        user1 = userCommandRepository.saveAndFlush(Member.builder()
                .account("qwerty1234")
                .password(encoder.encode("1234"))
                .name("김철수")
                .nickname("qwe")
                .email("qwe@qwe.qw")
                .role(Role.USER)
                .status(true)
                .build());

        user2 = userCommandRepository.saveAndFlush(Member.builder()
                .account("testUser")
                .password(encoder.encode("1111"))
                .name("testUUUUser")
                .nickname("user2닉네임입니다")
                .email("test@test.te")
                .role(Role.USER)
                .status(true)
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

        setUpFeedback1 = feedbackCommandRepository.saveAndFlush(feedback);

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

        setUpFeedback2 = feedbackCommandRepository.saveAndFlush(feedback2);
    }

    @Test
    @DisplayName("피드백 저장 테스트 : success")
    void saveFeedbackTest() {

        long before = feedbackCommandRepository.count();
        FeedbackCreateRequest feedbackCreateRequest = new FeedbackCreateRequest("제목", "내용", 0);
        FeedbackUserResponse user = apiFeedbackCommandInfraService.checkUser(user1.getId());

        feedbackCommandService.createFeedback(feedbackCreateRequest, user);

        long after = feedbackCommandRepository.count();
        Assertions.assertEquals(before + 1, after);

    }

    @Test
    @DisplayName("피드백 저장 테스트 : 제목 500자를 초과면 예외처리")
    void checkFeedbackTitleTest() {

        String str = "a".repeat(501);
        long before = feedbackCommandRepository.count();
        FeedbackCreateRequest feedbackCreateRequest = new FeedbackCreateRequest(str, "내용", 0);
        FeedbackUserResponse user = apiFeedbackCommandInfraService.checkUser(user1.getId());


        Assertions.assertThrows(IllegalArgumentException.class, () -> feedbackCommandService.createFeedback(feedbackCreateRequest, user));

        long after = feedbackCommandRepository.count();
        Assertions.assertEquals(before, after);

    }

    @Test
    @DisplayName("피드백 저장 테스트 : 내용 null이면 예외처리")
    void checkFeedbackContentTest() {

        String str = "";
        long before = feedbackCommandRepository.count();
        FeedbackCreateRequest feedbackCreateRequest = new FeedbackCreateRequest("제목", str, 0);
        FeedbackUserResponse user = apiFeedbackCommandInfraService.checkUser(user1.getId());

        Assertions.assertThrows(IllegalArgumentException.class, () -> feedbackCommandService.createFeedback(feedbackCreateRequest, user));

        long after = feedbackCommandRepository.count();
        Assertions.assertEquals(before, after);

    }

    @Test
    @DisplayName("피드백 저장 테스트 : 카테고리 번호가 올바르지않으면 예외처리")
    void checkFeedbackCategoryTest() {

        long before = feedbackCommandRepository.count();
        FeedbackCreateRequest feedbackCreateRequest = new FeedbackCreateRequest("제목", "내용", 1000);
        FeedbackUserResponse user = apiFeedbackCommandInfraService.checkUser(user1.getId());


        Assertions.assertThrows(IllegalArgumentException.class, () -> feedbackCommandService.createFeedback(feedbackCreateRequest, user));
        long after = feedbackCommandRepository.count();
        Assertions.assertEquals(before, after);
    }

    @Test
    @DisplayName("피드백 수정 테스트 : success")
    void checkFeedbackUpdateTest() {

        String title = "제목 수정해주세요";
        String content = "내용 수정할래요.";
        int category = 2;
        FeedbackUpdateRequest feedbackUpdateRequest = new FeedbackUpdateRequest(setUpFeedback1.getFeedbackNo(), title, content,
                category);
        FeedbackUserResponse user = apiFeedbackCommandInfraService.checkUser(user1.getId());

        feedbackCommandService.updateFeedback(feedbackUpdateRequest, user);

        Feedback updateFeedback = feedbackCommandRepository.findByFeedbackNo(setUpFeedback1.getFeedbackNo()).get();
        Assertions.assertEquals(title, updateFeedback.getFeedbackTitle());
        Assertions.assertEquals(content, updateFeedback.getFeedbackContent());
        Assertions.assertEquals(category, updateFeedback.getFeedbackCategory().getValue());

    }

//    @Test
//    @DisplayName("피드백 수정 테스트 : 작성자 불일치 시 예외처리")
//    void checkWriterFeedbackUpdateTest() {
//
//        String title = "제목 수정해주세요";
//        String content = "내용 수정할래요.";
//        int category = 1;
//
//        FeedbackUpdateRequest feedbackUpdateRequest = new FeedbackUpdateRequest(setUpFeedback1.getFeedbackNo(), title, content,
//                category);
//
//        Assertions.assertThrows(NotMatchWriterException.class, () -> feedbackCommandService.updateFeedback(feedbackUpdateRequest, user2.getId()));
//
//    }

    @Test
    @DisplayName("피드백 삭제 테스트 : success")
    void checkFeedbackDeleteTest() {

        long before = feedbackCommandRepository.count();
        FeedbackDeleteRequest feedbackDeleteRequest = new FeedbackDeleteRequest(setUpFeedback1.getFeedbackNo());
        FeedbackUserResponse user = apiFeedbackCommandInfraService.checkUser(user1.getId());

        feedbackCommandService.deleteFeedback(feedbackDeleteRequest, user);

        long after = feedbackCommandRepository.count();
        Assertions.assertEquals(before - 1, after);

    }

//    @Test
//    @DisplayName("피드백 삭제 테스트 : 작성자 불일치 시 예외처리")
//    void checkWriterFeedbackDeleteTest() {
//
//        FeedbackDeleteRequest feedbackDeleteRequest = new FeedbackDeleteRequest(setUpFeedback1.getFeedbackNo());
//
//        Assertions.assertThrows(NotMatchWriterException.class, () -> feedbackCommandService.deleteFeedback(feedbackDeleteRequest, user2.getId()));
//
//    }

}