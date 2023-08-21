package com.runninghi.comment.command.application.service;

import com.runninghi.comment.command.application.dto.request.CreateCommentRequest;
import com.runninghi.comment.command.domain.aggregate.entity.Comment;
import com.runninghi.comment.command.domain.repository.CommentRepository;
import com.runninghi.comment.command.domain.service.CommentCommandDomainService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CommentCommandService {

    private final CommentRepository commentRepository;
    private final CommentCommandDomainService domainService;

    @Transactional
    public Comment createComment(CreateCommentRequest commentDTO) {

        domainService.validateCommentContentNull(commentDTO.commentContent());
//        domainService.validateUser(commentDTO.userNo());
//        domainService.validateUserPost(commentDTO.userPostNo());

        return commentRepository.save(Comment.builder()
                .userNo(commentDTO.userNo())
                .userPostNo(commentDTO.userPostNo())
                .commentContent(commentDTO.commentContent())
                .commentDate(LocalDate.now())
                .build());
    }
}
