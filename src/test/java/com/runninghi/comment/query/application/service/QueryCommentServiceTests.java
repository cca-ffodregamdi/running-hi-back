package com.runninghi.comment.query.application.service;

import com.runninghi.comment.command.application.dto.request.CreateCommentRequest;
import com.runninghi.comment.command.application.service.CommandCommentService;
import com.runninghi.comment.command.domain.aggregate.entity.Comment;
import com.runninghi.comment.command.domain.repository.CommentRepository;
import com.runninghi.comment.query.application.dto.request.FindAllCommentsRequest;
import com.runninghi.comment.query.application.dto.request.FindCommentRequest;
import com.runninghi.feedback.command.domain.exception.customException.NotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
@Transactional
public class QueryCommentServiceTests {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private QueryCommentService queryCommentService;

    @Autowired
    private CommandCommentService createCommentService;

    @Test
    @DisplayName("조회: 댓글 전체 조회 기능 테스트")
    void testFindCommentsByPostNo() {
        Long userPostNo = 999L;

        commentRepository.save(Comment.builder()
                .userPostNo(userPostNo)
                .build());

        commentRepository.save(Comment.builder()
                .userPostNo(userPostNo)
                .build());

        Assertions.assertEquals(2, queryCommentService.findAllComments(new FindAllCommentsRequest(userPostNo)).size());

    }

    @Test
    @DisplayName("조회: 특정 댓글 조회 기능 테스트")
    void testFindCommentByCommentNo() {

        CreateCommentRequest commentRequest = new CreateCommentRequest(UUID.randomUUID(), 1L, "댓글 생성 테스트");
        Comment comment = createCommentService.createComment(commentRequest);

        Assertions.assertEquals(comment, queryCommentService.findComment(new FindCommentRequest(comment.getCommentNo())));

    }

    @Test
    @DisplayName("조회: 댓글 없을 시 예외처리")
    void testCommentNoDoesntExist() {

        FindCommentRequest commentRequest = new FindCommentRequest(0L);

        Assertions.assertThrows(NotFoundException.class, () -> {
            queryCommentService.findComment(commentRequest);
        });

    }

    @Test
    @DisplayName("조회: 게시글 없을 시 예외처리")
    void testPostNoDoesntExist() {

        //존재하지 않는 게시물일시 NotFoundException 처리

    }

}
