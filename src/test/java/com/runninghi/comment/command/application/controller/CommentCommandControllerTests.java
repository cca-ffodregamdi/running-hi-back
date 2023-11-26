package com.runninghi.comment.command.application.controller;

import com.runninghi.comment.command.application.dto.request.CreateCommentRequest;
import com.runninghi.comment.command.application.dto.request.DeleteCommentRequest;
import com.runninghi.comment.command.application.dto.request.UpdateCommentRequest;
import com.runninghi.comment.command.application.service.CommentCommandService;
import com.runninghi.comment.command.domain.aggregate.entity.Comment;
import com.runninghi.comment.command.domain.aggregate.vo.CommentMemberVO;
import com.runninghi.comment.command.domain.repository.CommentCommandRepository;
import com.runninghi.member.command.domain.aggregate.Member;
import com.runninghi.member.command.domain.aggregate.entity.enumtype.Role;
import com.runninghi.member.command.domain.repository.MemberCommandRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
public class CommentCommandControllerTests {

    @Autowired
    CommentCommandController commentCommandController;
    @MockBean
    CommentCommandService commentCommandService;
    private MockMvc mock;
    @Autowired
    private CommentCommandRepository commentRepository;

    @Autowired
    private MemberCommandRepository memberCommandRepository;
    @Autowired
    private PasswordEncoder encoder;

    @BeforeEach
    @AfterEach
    void clear() {
        commentRepository.deleteAllInBatch();
        memberCommandRepository.deleteAllInBatch();
    }

    @BeforeEach
    void setup() {
        mock = MockMvcBuilders.standaloneSetup(commentCommandController).build();
    }


    @Test
    @DisplayName("댓글 생성 컨트롤러 : success")
    void createCommentControllerTest() throws Exception {
        Member member = memberCommandRepository.save(Member.builder()
                .account("qwerty1234")
                .password(encoder.encode("1234"))
                .name("김철수")
                .nickname("qwe")
                .email("qwe@qwe.qw")
                .role(Role.USER)
                .status(true)
                .build());

        CreateCommentRequest request = new CreateCommentRequest(member.getId(), 1L, "댓글 생성 컨트롤러 테스트");

        mock.perform(post("/api/v1/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("댓글 수정 컨트롤러 : success")
    void updateCommentControllerTest() throws Exception {
        Comment comment = Comment.builder()
                .commentNo(999L)
                .commentDate(new Date())
                .commentContent("댓글 수정 컨트롤러 테스트")
                .memberNoVO(new CommentMemberVO(UUID.randomUUID()))
                .memberPostNo(111L)
                .build();

        UpdateCommentRequest request = new UpdateCommentRequest(comment.getCommentNo(), "댓글 수정!!!");

        mock.perform(put("/api/v1/comments/" + request.commentNo())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("댓글 삭제 컨트롤러 : success")
    void deleteCommentControllerTest() throws Exception {
        Comment comment = Comment.builder()
                .commentNo(999L)
                .commentDate(new Date())
                .commentContent("댓글 수정 컨트롤러 테스트")
                .memberNoVO(new CommentMemberVO(UUID.randomUUID()))
                .memberPostNo(111L)
                .build();

        DeleteCommentRequest request = new DeleteCommentRequest(comment.getCommentNo());

        mock.perform(delete("/api/v1/comments/" + request.commentNo())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(request)))
                .andExpect(status().isOk());
    }

}
