package com.runninghi.feedback.qeury.application.service;

import com.runninghi.feedback.command.domain.aggregate.entity.Feedback;
import com.runninghi.feedback.command.domain.aggregate.entity.FeedbackCategory;
import com.runninghi.feedback.command.domain.aggregate.vo.FeedbackWriterVO;
import com.runninghi.feedback.command.domain.repository.FeedbackCommandRepository;
import com.runninghi.feedback.query.application.dto.request.FeedbackFindRequest;
import com.runninghi.feedback.query.application.dto.request.FeedbackStatusRequest;
import com.runninghi.feedback.query.application.dto.response.FeedbackFindResponse;
import com.runninghi.feedback.query.application.dto.response.FeedbackStatusResponse;
import com.runninghi.feedback.query.application.dto.response.FeedbackUserResponse;
import com.runninghi.feedback.query.application.service.FeedbackQueryService;
import com.runninghi.feedback.query.domain.service.ApiFeedbackQueryDomainService;
import com.runninghi.member.command.domain.aggregate.entity.Member;
import com.runninghi.member.command.domain.aggregate.entity.enumtype.Role;
import com.runninghi.member.command.domain.repository.MemberCommandRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.List;

@SpringBootTest
@Transactional
public class FeedbackQueryServiceTests {

    @Autowired
    private ApiFeedbackQueryDomainService apiFeedbackQueryDomainService;

    @Autowired
    private FeedbackCommandRepository feedbackCommandRepository;

    @Autowired
    private FeedbackQueryService feedbackQueryService;

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
        userCommandRepository.deleteAll();
        feedbackCommandRepository.deleteAll();
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
    @DisplayName("피드백 조회 테스트 : 필터없이 조회 success")
    void checkFindFeedbackAllTest() {

        FeedbackFindRequest feedbackFindRequest = new FeedbackFindRequest(null, null);
        Pageable pageable = PageRequest.of(0, 1);

        Page<FeedbackFindResponse> feedbackPage = feedbackQueryService.findAllFeedback(feedbackFindRequest, pageable);

        Assertions.assertEquals(feedbackCommandRepository.count(), feedbackPage.getTotalElements());
        Assertions.assertEquals(2, feedbackPage.getTotalPages());

    }

    @Test
    @DisplayName("피드백 조회 테스트 : 카테고리로 조회 success")
    void checkFindFeedbackWithCategoryTest() {

        FeedbackFindRequest feedbackFindRequest = new FeedbackFindRequest(null, 1);
        Pageable pageable = PageRequest.of(0, 1);

        Page<FeedbackFindResponse> feedbackPage = feedbackQueryService.findAllFeedback(feedbackFindRequest, pageable);

        Assertions.assertEquals(1, feedbackPage.getTotalElements());
        Assertions.assertEquals(setUpFeedback1.getFeedbackTitle(), feedbackPage.getContent().get(0).feedbackTitle());
        Assertions.assertEquals(1, feedbackPage.getTotalPages());

    }

    @Test
    @DisplayName("피드백 조회 테스트 : 답변 여부로 조회 success")
    void checkFindFeedbackWithReplyStatusTest() {
        FeedbackStatusRequest feedbackStatusRequest1 = new FeedbackStatusRequest(true);
        FeedbackStatusRequest feedbackStatusRequest2 = new FeedbackStatusRequest(false);
        Pageable pageable = PageRequest.of(0, 1);

        Page<FeedbackStatusResponse> feedbackPage1 = feedbackQueryService.findFeedbackbyReplyStatus(feedbackStatusRequest1, pageable);
        Page<FeedbackStatusResponse> feedbackPage2 = feedbackQueryService.findFeedbackbyReplyStatus(feedbackStatusRequest2, pageable);

        Assertions.assertEquals(0, feedbackPage1.getTotalElements());
        Assertions.assertEquals(2, feedbackPage2.getTotalElements());
        Assertions.assertEquals(2, feedbackPage2.getTotalPages());

    }

    @Test
    @DisplayName("피드백 조회 테스트 : 본인의 피드백 전체 조회 success")
    void checkFindFeedbackMyPageTest() {

        FeedbackUserResponse feedbackUserResponse = apiFeedbackQueryDomainService.checkUser(user1.getId());
        Pageable pageable = PageRequest.of(0, 1);

        Page<Feedback> feedbackPage = feedbackQueryService.findFeedbackMyPage(feedbackUserResponse, pageable);
        List<Feedback> feedbackList = feedbackPage.stream().toList();

        Assertions.assertEquals(2, feedbackPage.getTotalElements());
        Assertions.assertEquals(setUpFeedback1.getFeedbackNo(), feedbackList.get(0).getFeedbackNo());
        Assertions.assertEquals(2, feedbackPage.getTotalPages());

    }

}
