package com.runninghi.comment.query.application.service;

import com.runninghi.comment.command.application.dto.request.CreateCommentRequest;
import com.runninghi.comment.command.application.dto.response.CommentCommandResponse;
import com.runninghi.comment.command.application.service.CommentCommandService;
import com.runninghi.comment.command.domain.aggregate.entity.Comment;
import com.runninghi.comment.command.domain.aggregate.vo.CommentMemberVO;
import com.runninghi.comment.command.domain.repository.CommentCommandRepository;
import com.runninghi.comment.query.application.dto.request.FindAllCommentsRequest;
import com.runninghi.comment.query.application.dto.request.FindCommentRequest;
import com.runninghi.comment.query.application.dto.response.CommentQueryResponse;
import com.runninghi.common.handler.feedback.customException.NotFoundException;
import com.runninghi.member.command.domain.aggregate.Member;
import com.runninghi.member.command.domain.aggregate.entity.enumtype.Role;
import com.runninghi.member.command.domain.repository.MemberCommandRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

@SpringBootTest
@Transactional
public class CommentQueryServiceTests {

    @Autowired
    private CommentCommandRepository commentCommandRepository;

    @Autowired
    private CommentQueryService queryCommentService;

    @Autowired
    private CommentCommandService createCommentService;

    @Autowired
    private MemberCommandRepository memberCommandRepository;

    private Member member;

    @BeforeEach
    @AfterEach
    void clear() {
        commentCommandRepository.deleteAllInBatch();
        memberCommandRepository.deleteAllInBatch();
    }

    @BeforeEach
    public void createUser() {

        UUID userId = UUID.randomUUID();

        member = memberCommandRepository.save(Member.builder()
                .id(userId)
                .account("qwerty1234")
                .password("Test")
                .name("김철수")
                .nickname("qwe")
                .email("qwe@qwe.qw")
                .role(Role.USER)
                .status(true)
                .build());
    }

    @Test
    @DisplayName("댓글 전체 조회 테스트 : success")
    void testFindCommentsByPostNo() {

        Long userPostNo = 999L;
        UUID userNo = UUID.randomUUID();

        commentCommandRepository.save(Comment.builder()
                .memberPostNo(userPostNo)
                .memberNoVO(new CommentMemberVO(userNo))
                .commentStatus(false)
                .build());

        commentCommandRepository.save(Comment.builder()
                .memberPostNo(userPostNo)
                .memberNoVO(new CommentMemberVO(userNo))
                .commentStatus(false)
                .build());

        Pageable pageable = PageRequest.of(0, 10);      //추후 수정 필요

        List<CommentQueryResponse> commentsList = queryCommentService.findAllComments(new FindAllCommentsRequest(userPostNo));

        Assertions.assertEquals(2, commentsList.size());

    }

    @Test
    @DisplayName("댓글 전체 조회 테스트 : status true 조회 불가능")
    void testStatusFalseComment() {

        Long userPostNo = 999L;

        commentCommandRepository.save(Comment.builder()
                .memberPostNo(userPostNo)
                .commentStatus(true)
                .build());

        Pageable pageable = PageRequest.of(0, 10);      //추후 수정 필요
        List<CommentQueryResponse> commentsList = queryCommentService.findAllComments(new FindAllCommentsRequest(userPostNo));

        Assertions.assertEquals(0, commentsList.size());

    }

    @Test
    @DisplayName("특정 댓글 조회 테스트 : success")
    void testFindCommentByCommentNo() {

        CreateCommentRequest commentRequest = new CreateCommentRequest(member.getId(), 1L, "댓글 생성 테스트");
        CommentCommandResponse comment = createCommentService.createComment(commentRequest);

        CommentQueryResponse response = queryCommentService.findComment(new FindCommentRequest(comment.commentNo()));

        Assertions.assertEquals(comment.commentNo(), response.commentNo());

    }

    @Test
    @DisplayName("댓글 조회 테스트: 댓글 없을 시 예외처리")
    void testCommentNoDoesntExist() {

        FindCommentRequest commentRequest = new FindCommentRequest(0L);

        Assertions.assertThrows(NotFoundException.class, () -> {
            queryCommentService.findComment(commentRequest);
        });

    }

    @Test
    @DisplayName("댓글 조회 테스트: 게시글 없을 시 예외처리")
    void testPostNoDoesntExist() {

        //존재하지 않는 게시물일시 NotFoundException 처리

    }

}
