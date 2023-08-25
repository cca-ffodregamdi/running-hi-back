package com.runninghi.comment.command.application.service;

import com.runninghi.comment.command.application.dto.request.CreateCommentRequest;
import com.runninghi.comment.command.application.dto.request.DeleteCommentRequest;
import com.runninghi.comment.command.application.dto.request.UpdateCommentRequest;
import com.runninghi.comment.command.application.dto.response.CommentCommandResponse;
import com.runninghi.comment.command.application.dto.response.CommentDeleteResponse;
import com.runninghi.comment.command.domain.aggregate.vo.CommentUserVO;
import com.runninghi.comment.command.domain.repository.CommentCommandRepository;
import com.runninghi.comment.query.application.dto.request.FindCommentRequest;
import com.runninghi.comment.query.application.service.CommentQueryService;
import com.runninghi.comment.query.infrastructure.repository.CommentQueryRepository;
import com.runninghi.common.handler.feedback.customException.NotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Date;
import java.util.UUID;


@SpringBootTest
@Transactional
public class CommentCommandServiceTests {

    @Autowired
    private CommentCommandService commentCommandService;

    @Autowired
    private CommentCommandRepository commentRepository;

    @Autowired
    private CommentQueryRepository commentQueryRepository;

    @Autowired
    private CommentQueryService commentQueryService;

    @BeforeEach
    void clear() {
        commentRepository.deleteAllInBatch();
    }


    @Test
    @DisplayName("댓글 생성 테스트 : success")
    void testCreateComment() {

        long beforeSize = commentQueryRepository.count();

        CreateCommentRequest commentRequest = new CreateCommentRequest(new CommentUserVO(UUID.randomUUID()), 1L, "댓글 생성 테스트");
        commentCommandService.createComment(commentRequest);

        long afterSize = commentQueryRepository.count();

        Assertions.assertEquals(beforeSize + 1, afterSize);
    }

    @Test
    @DisplayName("댓글 생성 테스트: 댓글 내용 공백일 때 예외처리")
    void testCommentIsBlank() {

        CreateCommentRequest commentRequest = new CreateCommentRequest(new CommentUserVO(UUID.randomUUID()), 1L, "         ");
        assertThatThrownBy(() -> commentCommandService.createComment(commentRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("댓글은 공백일 수 없습니다.");
    }

    @Test
    @DisplayName("댓글 생성 테스트: 댓글 내용 null일 때 예외처리")
    void testCommentIsNull() {

        CreateCommentRequest commentRequest = new CreateCommentRequest(new CommentUserVO(UUID.randomUUID()), 1L, null);
        assertThatThrownBy(() -> commentCommandService.createComment(commentRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("댓글은 공백일 수 없습니다.");
    }

    @Test
    @DisplayName("댓글 삭제 테스트 : success - response true")
    void testDeleteCommentResponseTrue() {

        CreateCommentRequest commentRequest = new CreateCommentRequest(new CommentUserVO(UUID.randomUUID()), 1L, "댓글 생성 테스트");
        CommentCommandResponse comment = commentCommandService.createComment(commentRequest);

        CommentDeleteResponse response = commentCommandService.deleteComment(new DeleteCommentRequest(comment.commentNo()));

        Assertions.assertTrue(response.result());
    }

    @Test
    @DisplayName("댓글 삭제 테스트 : success - 조회 시 예외처리")
    void testDeleteCommentFindException() {

        CreateCommentRequest commentRequest = new CreateCommentRequest(new CommentUserVO(UUID.randomUUID()), 1L, "댓글 생성 테스트");
        CommentCommandResponse comment = commentCommandService.createComment(commentRequest);

        CommentDeleteResponse response = commentCommandService.deleteComment(new DeleteCommentRequest(comment.commentNo()));

        assertThatThrownBy(() -> commentQueryService.findComment(new FindCommentRequest(comment.commentNo())))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 댓글 입니다.");
    }

    @Test
    @DisplayName("댓글 삭제 테스트 : 존재하지 않는 댓글 예외처리")
    void testDeleteCommentDoesntExist() {

        assertThatThrownBy(() -> commentCommandService.deleteComment(new DeleteCommentRequest(0L)))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 댓글 입니다.");
    }

    @Test
    @DisplayName("댓글 수정 테스트 : success")
    void testUpdateComment() throws InterruptedException {
        CreateCommentRequest commentRequest = new CreateCommentRequest(new CommentUserVO(UUID.randomUUID()), 1L, "댓글 생성 테스트");
        CommentCommandResponse comment = commentCommandService.createComment(commentRequest);
        Date date = comment.commentDate();

        Thread.sleep(1000);

        commentCommandService.updateComment(new UpdateCommentRequest(comment.commentNo(), "댓글 수정 테스트 입니다."));

        Assertions.assertSame(commentQueryService.findComment(new FindCommentRequest(comment.commentNo())).commentContent(),
                "댓글 수정 테스트 입니다.");
        Assertions.assertNotSame(date, commentQueryService.findComment(new FindCommentRequest(comment.commentNo())).commentDate());
    }

    @Test
    @DisplayName("댓글 수정 테스트 : 댓글 공백 시 예외처리")
    void testContentIsBlank() {
        CreateCommentRequest commentRequest = new CreateCommentRequest(new CommentUserVO(UUID.randomUUID()), 1L, "댓글 생성 테스트");
        CommentCommandResponse comment = commentCommandService.createComment(commentRequest);

        UpdateCommentRequest updateRequest = new UpdateCommentRequest(comment.commentNo(), "          ");

        assertThatThrownBy(() -> commentCommandService.updateComment(updateRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("댓글은 공백일 수 없습니다.");
    }

    @Test
    @DisplayName("댓글 수정 테스트 : 댓글 null 시 예외처리")
    void testContentIsNull() {
        CreateCommentRequest commentRequest = new CreateCommentRequest(new CommentUserVO(UUID.randomUUID()), 1L, "댓글 생성 테스트");
        CommentCommandResponse comment = commentCommandService.createComment(commentRequest);

        UpdateCommentRequest updateRequest = new UpdateCommentRequest(comment.commentNo(), "");

        assertThatThrownBy(() -> commentCommandService.updateComment(updateRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("댓글은 공백일 수 없습니다.");
    }
}
