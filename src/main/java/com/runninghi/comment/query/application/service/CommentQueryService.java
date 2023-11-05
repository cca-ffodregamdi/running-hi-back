package com.runninghi.comment.query.application.service;

import com.runninghi.bookmark.command.domain.service.BookmarkCommandDomainService;
import com.runninghi.comment.command.domain.aggregate.entity.Comment;
import com.runninghi.comment.query.application.dto.request.FindAllCommentsRequest;
import com.runninghi.comment.query.application.dto.request.FindCommentRequest;
import com.runninghi.comment.query.application.dto.response.CommentQueryResponse;
import com.runninghi.comment.query.infrastructure.repository.CommentQueryRepository;
import com.runninghi.common.handler.feedback.customException.NotFoundException;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentQueryService {

    private final CommentQueryRepository commentQueryRepository;
    private final BookmarkCommandDomainService domainService;

    @Transactional(readOnly = true)
    public List<CommentQueryResponse> findAllComments(FindAllCommentsRequest commentDTO) {

//        domainService.validatePostExist(commentDTO.userPostNo());

        Specification<Comment> filter = (root, query, criteriaBuilder) -> {
            Predicate statusPredicate = criteriaBuilder.equal(root.get("commentStatus"), false);
            Predicate postNoPredicate = criteriaBuilder.equal(root.get("userPostNo"), commentDTO.userPostNo());
            return criteriaBuilder.and(statusPredicate, postNoPredicate);
        };

//        Pageable sortedPage = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()); //추후에 sort 설정

//        return commentQueryRepository.findAll(filter, sortedPage);  // 추후에 pageable 적용
//        return commentRepository.findAllByUserPostNo(commentDTO.userPostNo());

//        return commentQueryRepository.findAllComments(commentDTO.userPostNo());
        List<Comment> comments = commentQueryRepository.findAll(filter);
        return CommentQueryResponse.from(comments);
    }

    @Transactional(readOnly = true)
    public CommentQueryResponse findComment(FindCommentRequest commentDTO) {

       Comment comment = commentQueryRepository.findById(commentDTO.commentNo())
               .orElseThrow(() -> new NotFoundException("존재하지 않는 댓글 입니다."));

       return CommentQueryResponse.from(comment);
    }
}
