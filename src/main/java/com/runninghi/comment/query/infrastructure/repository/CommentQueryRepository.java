package com.runninghi.comment.query.infrastructure.repository;

import com.runninghi.comment.command.domain.aggregate.entity.Comment;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentQueryRepository  extends JpaRepository<Comment, Long>, JpaSpecificationExecutor<Comment> {

    @Query("SELECT c FROM Comment c WHERE c.commentStatus = false AND c.userPostNo = :userPostNo")
    List<Comment> findAllComments(@Param("userPostNo") Long userPostNo);
}
