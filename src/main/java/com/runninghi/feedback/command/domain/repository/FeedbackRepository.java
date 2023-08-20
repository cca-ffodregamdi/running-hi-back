package com.runninghi.feedback.command.domain.repository;

import com.runninghi.feedback.command.domain.aggregate.entity.Feedback;
import com.runninghi.feedback.command.domain.aggregate.entity.FeedbackCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

    // FeedbackNo로 피드백 하나 조회
    Optional<Feedback> findByFeedbackNo(Long feedbackNo);

    // 피드백 목록 전체 조회
    @Query(value = "SELECT f.*" +
            "FROM tbl_feedback f " +
            "JOIN tbl_user u ON f.feedback_writer_id = u.id",
            countQuery = "SELECT COUNT(*) FROM feedback f INNER JOIN user u ON f.feedback_writer_id = u.id",
            nativeQuery = true)
    Page<Feedback> findAllFeedback(Pageable pageable);

    // 피드백 카테고리로 필터링해서 전체 조회
    Page<Feedback> findFeedbacksByFeedbackCategory(FeedbackCategory feedbackCategory, Pageable pageable);

}
