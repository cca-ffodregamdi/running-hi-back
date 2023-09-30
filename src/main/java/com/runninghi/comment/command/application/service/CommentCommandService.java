package com.runninghi.comment.command.application.service;

import com.runninghi.comment.command.application.dto.request.CreateCommentRequest;
import com.runninghi.comment.command.application.dto.request.DeleteCommentRequest;
import com.runninghi.comment.command.application.dto.request.UpdateCommentRequest;
import com.runninghi.comment.command.application.dto.response.CommentCommandResponse;
import com.runninghi.comment.command.application.dto.response.CommentDeleteResponse;
import com.runninghi.comment.command.domain.aggregate.entity.Comment;
import com.runninghi.comment.command.domain.aggregate.vo.CommentUserVO;
import com.runninghi.comment.command.domain.repository.CommentCommandRepository;
import com.runninghi.comment.command.domain.service.CommentCommandDomainService;
import com.runninghi.common.handler.feedback.customException.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class CommentCommandService {

    private final CommentCommandRepository commentRepository;
    private final CommentCommandDomainService commentDomainService;

    @Transactional
    public CommentCommandResponse createComment(CreateCommentRequest commentDTO) {

        commentDomainService.validateCommentContentNull(commentDTO.commentContent());
//        domainService.validateUser(commentDTO.userNo());
//        domainService.validateUserPost(commentDTO.userPostNo());

        Comment comment = commentRepository.save(Comment.builder()
                .userNoVO(new CommentUserVO(commentDTO.userNo()))
                .userPostNo(commentDTO.userPostNo())
                .commentContent(commentDTO.commentContent())
                .commentDate(new Date())
                .build());

        return CommentCommandResponse.from(comment);
    }

    @Transactional
    public CommentDeleteResponse deleteComment(DeleteCommentRequest commentDTO) {

        commentDomainService.validateComment(commentDTO.commentNo());

        commentRepository.deleteById(commentDTO.commentNo());

        return new CommentDeleteResponse(true);

    }

    @Transactional
    public CommentCommandResponse updateComment(UpdateCommentRequest commentDTO) {
        commentDomainService.validateCommentContentNull(commentDTO.commentContent());
        Comment comment =  commentRepository.findById(commentDTO.commentNo())
                    .orElseThrow(() -> new NotFoundException("존재하지 않는 댓글 입니다."));
        comment.update(commentDTO);

        return CommentCommandResponse.from(comment);
    }
}
