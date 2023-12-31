package com.runninghi.feedback.command.application.service;

import com.runninghi.feedback.command.application.dto.request.FeedbackReplyCreateRequest;
import com.runninghi.feedback.command.application.dto.request.FeedbackReplyDeleteRequest;
import com.runninghi.feedback.command.application.dto.request.FeedbackReplyUpdateRequest;
import com.runninghi.feedback.command.application.dto.response.FeedbackResponse;
import com.runninghi.feedback.command.domain.aggregate.entity.Feedback;
import com.runninghi.feedback.command.domain.aggregate.entity.FeedbackCategory;
import com.runninghi.feedback.command.domain.aggregate.vo.FeedbackWriterVO;
import com.runninghi.common.handler.feedback.customException.NotFoundException;
import com.runninghi.feedback.command.domain.repository.FeedbackCommandRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@Transactional
public class FeedbackReplyCommandServiceTests {

    @Autowired
    private FeedbackReplyCommandService feedbackReplyCommandService;

    @Autowired
    private FeedbackCommandRepository feedbackCommandRepository;

    private Feedback setUpFeedback;

    @BeforeEach
    @AfterEach
    void clear() {
        feedbackCommandRepository.deleteAll();
    }

    @BeforeEach
    public void setUp() {

        FeedbackWriterVO feedbackWriterVO = new FeedbackWriterVO(UUID.fromString("ac60fb25-b40b-4308-827a-5aba81860fcb"));
        FeedbackCategory feedbackCategory = FeedbackCategory.fromValue(2);

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

        setUpFeedback = feedbackCommandRepository.save(feedback);

    }

    @Test
    @DisplayName("피드백 답변 저장 테스트 : success")
    void saveFeedbackReplyTest() {

        String feedbackReply = "피드백 답변";
        FeedbackReplyCreateRequest feedbackReplyCreateRequest = new FeedbackReplyCreateRequest(setUpFeedback.getFeedbackNo(), feedbackReply);
        FeedbackResponse feedbackResponse = feedbackReplyCommandService.createFeedbackReply(feedbackReplyCreateRequest);

        Optional<Feedback> feedbackOptional = feedbackCommandRepository.findByFeedbackNo(feedbackResponse.feedbackNo());
        Feedback feedback = feedbackOptional.get();

        Assertions.assertEquals(feedbackReply, feedback.getFeedbackReply());
        Assertions.assertEquals(setUpFeedback.getFeedbackNo(), feedback.getFeedbackNo());

    }

    @Test
    @DisplayName("피드백 답변 저장 테스트 : 피드백 없을 시 예외처리")
    void checkFeedbackExistInSaveFeedbackReplyTest() {

        String feedbackReply = "피드백 답변";
        FeedbackReplyCreateRequest feedbackReplyCreateRequest = new FeedbackReplyCreateRequest(setUpFeedback.getFeedbackNo() + 1, feedbackReply);

        Assertions.assertThrows(NotFoundException.class, () -> feedbackReplyCommandService.createFeedbackReply(feedbackReplyCreateRequest));

    }

    @Test
    @DisplayName("피드백 답변 수정 테스트 : success")
    void updateFeedbackReplyTest() throws InterruptedException {

        String feedbackReply = "피드백 답변 수정 수정";
        Date date = setUpFeedback.getFeedbackReplyDate();
        FeedbackReplyUpdateRequest feedbackReplyUpdateRequest = new FeedbackReplyUpdateRequest(setUpFeedback.getFeedbackNo(), feedbackReply);

        Thread.sleep(1000);
        FeedbackResponse feedbackResponse = feedbackReplyCommandService.updateFeedbackReply(feedbackReplyUpdateRequest);

        Feedback updateFeedback = feedbackCommandRepository.findByFeedbackNo(feedbackResponse.feedbackNo()).get();

        Assertions.assertEquals(feedbackReply, updateFeedback.getFeedbackReply());
        Assertions.assertNotEquals(date, updateFeedback.getFeedbackReplyDate());

    }

    @Test
    @DisplayName("피드백 답변 수정 테스트 : 피드백 없을 시 예외처리")
    void checkFeedbackExistInupdateFeedbackReplyTest() {

        String feedbackReply = "피드백 답변";
        FeedbackReplyUpdateRequest feedbackReplyUpdateRequest = new FeedbackReplyUpdateRequest(setUpFeedback.getFeedbackNo() + 1, feedbackReply);

        Assertions.assertThrows(NotFoundException.class, () -> feedbackReplyCommandService.updateFeedbackReply(feedbackReplyUpdateRequest));

    }

    @Test
    @DisplayName("피드백 답변 삭제 테스트 : success")
    void deleteFeedbackReplyTest() {

        FeedbackReplyDeleteRequest feedbackReplyDeleteRequest = new FeedbackReplyDeleteRequest(setUpFeedback.getFeedbackNo());

        feedbackReplyCommandService.deleteFeedbackReply(feedbackReplyDeleteRequest);

        Feedback feedback = feedbackCommandRepository.findByFeedbackNo(setUpFeedback.getFeedbackNo()).get();

        Assertions.assertNull(feedback.getFeedbackReply());
        Assertions.assertNull(feedback.getFeedbackReplyDate());

    }

    @Test
    @DisplayName("피드백 답변 삭제 테스트 : 피드백 없을 시 예외처리")
    void checkFeedbackExistInDeleteFeedbackReplyTest() {

        long before = feedbackCommandRepository.count();

        FeedbackReplyDeleteRequest feedbackReplyDeleteRequest = new FeedbackReplyDeleteRequest(before + 1);

        Assertions.assertThrows(NotFoundException.class, () -> feedbackReplyCommandService.deleteFeedbackReply(feedbackReplyDeleteRequest));

    }
}
