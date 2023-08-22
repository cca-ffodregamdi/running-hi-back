package com.runninghi.comment.command.application.service;

import com.runninghi.comment.command.application.dto.request.CreateCommentRequest;
import com.runninghi.comment.command.application.dto.request.DeleteCommentRequest;
import com.runninghi.comment.command.application.dto.request.UpdateCommentRequest;
import com.runninghi.comment.command.domain.aggregate.entity.Comment;
import com.runninghi.comment.command.domain.repository.CommentRepository;
import com.runninghi.comment.command.domain.service.CommentCommandDomainService;
import com.runninghi.common.handler.feedback.customException.NotFoundException;
import com.runninghi.user.command.application.dto.user.response.UserUpdateResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class CommentCommandService {

    private final CommentRepository commentRepository;
    private final CommentCommandDomainService commentDomainService;

    @Transactional
    public Comment createComment(CreateCommentRequest commentDTO) {

        commentDomainService.validateCommentContentNull(commentDTO.commentContent());
//        domainService.validateUser(commentDTO.userNo());
//        domainService.validateUserPost(commentDTO.userPostNo());

        return commentRepository.save(Comment.builder()
                .userNo(commentDTO.userNo())
                .userPostNo(commentDTO.userPostNo())
                .commentContent(commentDTO.commentContent())
                .commentDate(new Date())
                .build());
    }

    @Transactional
    public void deleteComment(DeleteCommentRequest commentDTO) {

        commentDomainService.validateComment(commentDTO.commentNo());

        commentRepository.deleteById(commentDTO.commentNo());
    }

    @Transactional
    public void updateComment(UpdateCommentRequest commentDTO) {
        commentDomainService.validateCommentContentNull(commentDTO.commentContent());
        Comment comment =  commentRepository.findById(commentDTO.commentNo())
                    .orElseThrow(() -> new NotFoundException("존재하지 않는 댓글 입니다."));
        comment.update(commentDTO);
    }
}
