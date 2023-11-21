package com.runninghi.feedback.command.domain.repository;

import com.runninghi.feedback.command.domain.aggregate.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeedbackCommandRepository extends JpaRepository<Feedback, Long> {

    // FeedbackNo로 피드백 하나 조회
    Optional<Feedback> findByFeedbackNo(Long feedbackNo);

}
