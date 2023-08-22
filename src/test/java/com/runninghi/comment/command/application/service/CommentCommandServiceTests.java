package com.runninghi.comment.command.application.service;

import com.runninghi.comment.command.application.dto.request.CreateCommentRequest;
import com.runninghi.comment.command.application.dto.request.DeleteCommentRequest;
import com.runninghi.comment.command.application.dto.request.UpdateCommentRequest;
import com.runninghi.comment.command.domain.aggregate.entity.Comment;
import com.runninghi.comment.command.domain.repository.CommentRepository;
import com.runninghi.comment.query.application.dto.request.FindCommentRequest;
import com.runninghi.comment.query.application.service.CommentQueryService;
import com.runninghi.common.handler.feedback.customException.NotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;


@SpringBootTest
@Transactional
public class CommentCommandServiceTests {

    @Autowired
    private CommentCommandService commentCommandService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentQueryService commentQueryService;

    @BeforeEach
    void clear() {
        commentRepository.deleteAllInBatch();
    }


    @Test
    @DisplayName("댓글 생성 테스트 : success")
    void testCreateComment() {

        long beforeSize = commentRepository.count();

        CreateCommentRequest commentRequest = new CreateCommentRequest(UUID.randomUUID(), 1L, "댓글 생성 테스트");
        commentCommandService.createComment(commentRequest);

        long afterSize = commentRepository.count();

        org.junit.jupiter.api.Assertions.assertEquals(beforeSize + 1, afterSize);
    }

    @Test
    @DisplayName("댓글 생성 테스트: 댓글 내용 공백일 때 예외처리")
    void testCommentIsBlank() {

        CreateCommentRequest commentRequest = new CreateCommentRequest(UUID.randomUUID(), 1L, "         ");
        org.assertj.core.api.Assertions.assertThatThrownBy(() -> commentCommandService.createComment(commentRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("댓글은 공백일 수 없습니다.");
    }

    @Test
    @DisplayName("댓글 생성 테스트: 댓글 내용 null일 때 예외처리")
    void testCommentIsNull() {

        CreateCommentRequest commentRequest = new CreateCommentRequest(UUID.randomUUID(), 1L, null);
        org.assertj.core.api.Assertions.assertThatThrownBy(() -> commentCommandService.createComment(commentRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("댓글은 공백일 수 없습니다.");
    }

    @Test
    @DisplayName("댓글 삭제 테스트 : success")
    void testDeleteComment() {

        CreateCommentRequest commentRequest = new CreateCommentRequest(UUID.randomUUID(), 1L, "댓글 생성 테스트");
        Comment comment = commentCommandService.createComment(commentRequest);

        commentCommandService.deleteComment(new DeleteCommentRequest(comment.getCommentNo()));

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> commentQueryService.findComment(new FindCommentRequest(comment.getCommentNo())))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 댓글 입니다.");
    }

    @Test
    @DisplayName("댓글 삭제 테스트 : 존재하지 않는 댓글 예외처리")
    void testDeleteCommentDoesntExist() {

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> commentCommandService.deleteComment(new DeleteCommentRequest(0L)))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 댓글 입니다.");
    }

    @Test
    @DisplayName("댓글 수정 테스트 : success")
    void testUpdateComment() {
        CreateCommentRequest commentRequest = new CreateCommentRequest(UUID.randomUUID(), 1L, "댓글 생성 테스트");
        Comment comment = commentCommandService.createComment(commentRequest);

        commentCommandService.updateComment(new UpdateCommentRequest(comment.getCommentNo(), "댓글 수정 테스트 입니다."));

        Assertions.assertSame(commentQueryService.findComment(new FindCommentRequest(comment.getCommentNo())).getCommentContent(),
                "댓글 수정 테스트 입니다.");
    }

    @Test
    @DisplayName("댓글 수정 테스트 : 댓글 공백 시 예외처리")
    void testContentIsBlank() {
        CreateCommentRequest commentRequest = new CreateCommentRequest(UUID.randomUUID(), 1L, "댓글 생성 테스트");
        Comment comment = commentCommandService.createComment(commentRequest);

        UpdateCommentRequest updateRequest = new UpdateCommentRequest(comment.getCommentNo(), "          ");

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> commentCommandService.updateComment(updateRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("댓글은 공백일 수 없습니다.");
    }

    @Test
    @DisplayName("댓글 수정 테스트 : 댓글 null 시 예외처리")
    void testContentIsNull() {
        CreateCommentRequest commentRequest = new CreateCommentRequest(UUID.randomUUID(), 1L, "댓글 생성 테스트");
        Comment comment = commentCommandService.createComment(commentRequest);

        UpdateCommentRequest updateRequest = new UpdateCommentRequest(comment.getCommentNo(), "");

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> commentCommandService.updateComment(updateRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("댓글은 공백일 수 없습니다.");
    }
}
