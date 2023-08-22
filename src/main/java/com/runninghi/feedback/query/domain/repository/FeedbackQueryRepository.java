package com.runninghi.feedback.query.domain.repository;

import com.runninghi.feedback.command.domain.aggregate.entity.Feedback;
import com.runninghi.feedback.command.domain.aggregate.entity.FeedbackCategory;
import com.runninghi.feedback.command.domain.aggregate.vo.FeedbackWriterVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeedbackQueryRepository extends JpaRepository<Feedback, Integer> {

    // FeedbackNo로 피드백 하나 조회
    Optional<Feedback> findByFeedbackNo(Long feedbackNo);

    // 피드백 목록 전체 조회
    Page<Feedback> findAll(Pageable pageable);

    // 피드백 카테고리로 필터링해서 전체 조회
    Page<Feedback> findFeedbacksByFeedbackCategory(FeedbackCategory feedbackCategory, Pageable pageable);

    // 답변 여부로 필터링해서 전체 조회
    Page<Feedback> findFeedbacksByFeedbackStatus(boolean feedbackStatus, Pageable pageable);

    // 유저 본인의 피드백 전체 조회
    Page<Feedback> findFeedbacksByFeedbackWriterVO(FeedbackWriterVO feedbackWriterVO, Pageable pageable);

}
