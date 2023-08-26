package com.runninghi.comment.query.infrastructure.repository;

import com.runninghi.comment.command.domain.aggregate.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentQueryRepository  extends JpaRepository<Comment, Long> {

}
