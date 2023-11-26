package com.runninghi.comment.command.domain.repository;

import com.runninghi.comment.command.domain.aggregate.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentCommandRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByMemberPostNo(Long memberPostNo);
}
