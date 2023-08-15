package com.runninghi.feedback.command.service;

import com.runninghi.feedback.command.application.dto.FeedbackNoDTO;
import com.runninghi.feedback.command.application.dto.FeedbackReplyDTO;
import com.runninghi.feedback.command.domain.aggregate.entity.Feedback;
import com.runninghi.feedback.command.domain.aggregate.entity.FeedbackCategory;
import com.runninghi.feedback.command.domain.aggregate.vo.FeedbackWriterVO;
import com.runninghi.feedback.command.domain.exception.customException.NotFoundException;
import com.runninghi.feedback.command.domain.repository.FeedbackRepository;
import com.runninghi.feedback.command.domain.service.FeedbackReplyService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;

@SpringBootTest
@Transactional
public class FeedbackReplyServiceTests {

    @Autowired
    private FeedbackReplyService feedbackReplyService;

    @Autowired
    private FeedbackRepository feedbackRepository;

    private Feedback setUpFeedback;

    @BeforeEach
    public void setUp() {
        FeedbackWriterVO feedbackWriterVO = new FeedbackWriterVO(1L);
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

        setUpFeedback = feedbackRepository.save(feedback);
    }

    @Test
    @DisplayName("피드백 답변 저장 테스트 : success")
    void saveFeedbackReplyTest() {

        String feedbackReply = "피드백 답변";
        FeedbackReplyDTO feedbackReplyDTO = new FeedbackReplyDTO(setUpFeedback.getFeedbackNo(), feedbackReply);
        Long saveFeedbackNo = feedbackReplyService.saveFeedbackReply(feedbackReplyDTO);

        Optional<Feedback> feedbackOptional = feedbackRepository.findByFeedbackNo(saveFeedbackNo);
        Feedback feedback = feedbackOptional.get();

        Assertions.assertEquals(feedbackReply, feedback.getFeedbackReply());
        Assertions.assertEquals(setUpFeedback.getFeedbackNo(), feedback.getFeedbackNo());

    }

    @Test
    @DisplayName("피드백 답변 저장 테스트 : 피드백 없음")
    void checkFeedbackExistInSaveFeedbackReplyTest() {

        String feedbackReply = "피드백 답변";
        FeedbackReplyDTO feedbackReplyDTO = new FeedbackReplyDTO(setUpFeedback.getFeedbackNo() + 1, feedbackReply);

        Assertions.assertThrows(NotFoundException.class, () -> feedbackReplyService.saveFeedbackReply(feedbackReplyDTO));

    }

    @Test
    @DisplayName("피드백 답변 수정 테스트 : success")
    void modifyFeedbackReplyTest() throws InterruptedException {

        String feedbackReply = "피드백 답변 수정 수정";
        Date date = setUpFeedback.getFeedbackReplyDate();
        FeedbackReplyDTO feedbackReplyDTO = new FeedbackReplyDTO(setUpFeedback.getFeedbackNo(), feedbackReply);

        Thread.sleep(1000);
        Long modifyFeedbackNo = feedbackReplyService.modifyFeedbackReply(feedbackReplyDTO);

        Feedback modifyFeedback = feedbackRepository.findByFeedbackNo(modifyFeedbackNo ).get();

        Assertions.assertEquals(feedbackReply, modifyFeedback.getFeedbackReply());
        Assertions.assertNotEquals(date, modifyFeedback.getFeedbackReplyDate());
    }

    @Test
    @DisplayName("피드백 답변 수정 테스트 : 피드백 없음")
    void checkFeedbackExistInModifyFeedbackReplyTest() {

        String feedbackReply = "피드백 답변";
        FeedbackReplyDTO feedbackReplyDTO = new FeedbackReplyDTO(setUpFeedback.getFeedbackNo() + 1, feedbackReply);

        Assertions.assertThrows(NotFoundException.class, () -> feedbackReplyService.modifyFeedbackReply(feedbackReplyDTO));
    }

    @Test
    @DisplayName("피드백 답변 삭제 테스트 : success")
    void deleteFeedbackReplyTest() {

        FeedbackNoDTO feedbackNoDTO = new FeedbackNoDTO(setUpFeedback.getFeedbackNo());

        feedbackReplyService.deleteFeedbackReply(feedbackNoDTO);

        Feedback feedback = feedbackRepository.findByFeedbackNo(setUpFeedback.getFeedbackNo()).get();

        Assertions.assertNull(feedback.getFeedbackReply());
        Assertions.assertNull(feedback.getFeedbackReplyDate());

    }

    @Test
    @DisplayName("피드백 답변 삭제 테스트 : 피드백 없음")
    void checkFeedbackExistInDeleteFeedbackReplyTest() {
        long before = feedbackRepository.count();

        FeedbackNoDTO feedbackNoDTO = new FeedbackNoDTO(before + 1);

        Assertions.assertThrows(NotFoundException.class, () -> feedbackReplyService.deleteFeedbackReply(feedbackNoDTO));
    }
}
