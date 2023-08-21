package com.runninghi.comment.query.application.service;

import com.runninghi.bookmark.command.domain.service.CommandBookmarkDomainService;
import com.runninghi.comment.command.domain.aggregate.entity.Comment;
import com.runninghi.comment.command.domain.repository.CommentRepository;
import com.runninghi.comment.query.application.dto.request.FindAllCommentsRequest;
import com.runninghi.comment.query.application.dto.request.FindCommentRequest;
import com.runninghi.common.handler.feedback.customException.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryCommentService {

    private final CommentRepository commentRepository;
    private final CommandBookmarkDomainService domainService;

    public List<Comment> findAllComments(FindAllCommentsRequest commentDTO) {

//        domainService.validatePostExist(commentDTO.userPostNo());

        return commentRepository.findAllByUserPostNo(commentDTO.userPostNo());
    }

    public Comment findComment(FindCommentRequest commentDTO) {

       return commentRepository.findById(commentDTO.commentNo())
               .orElseThrow(() -> new NotFoundException("존재하지 않는 댓글 입니다."));
    }
}
