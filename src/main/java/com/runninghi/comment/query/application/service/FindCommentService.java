package com.runninghi.comment.query.application.service;

import com.runninghi.bookmark.command.domain.service.BookmarkDomainService;
import com.runninghi.comment.command.domain.aggregate.entity.Comment;
import com.runninghi.comment.command.domain.repository.CommentRepository;
import com.runninghi.comment.query.application.dto.request.FindCommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindCommentService {

    private final CommentRepository commentRepository;
    private final BookmarkDomainService domainService;

    public List<Comment> findAllComments(FindCommentRequest commentDTO) {

//        domainService.validatePostExist(commentDTO.userPostNo());

        return commentRepository.findAllByUserPostNo(commentDTO.userPostNo());
    }
}
