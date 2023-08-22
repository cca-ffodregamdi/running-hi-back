package com.runninghi.comment.query.application.service;

import com.runninghi.bookmark.command.domain.service.BookmarkCommandDomainService;
import com.runninghi.comment.command.domain.aggregate.entity.Comment;
import com.runninghi.comment.command.domain.repository.CommentRepository;
import com.runninghi.comment.query.application.dto.request.FindAllCommentsRequest;
import com.runninghi.comment.query.application.dto.request.FindCommentRequest;
import com.runninghi.common.handler.feedback.customException.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentQueryService {

    private final CommentRepository commentRepository;
    private final BookmarkCommandDomainService domainService;

    @Transactional(readOnly = true)
    public Page<Comment> findAllComments(FindAllCommentsRequest commentDTO, Pageable pageable) {

//        domainService.validatePostExist(commentDTO.userPostNo());

        Pageable sortedPage = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()); //추후에 sort 설정
        return commentRepository.findAll(sortedPage);
//        return commentRepository.findAllByUserPostNo(commentDTO.userPostNo());
    }

    @Transactional(readOnly = true)
    public Comment findComment(FindCommentRequest commentDTO) {

       return commentRepository.findById(commentDTO.commentNo())
               .orElseThrow(() -> new NotFoundException("존재하지 않는 댓글 입니다."));
    }
}
