package com.runninghi.comment.query.application.service;

import com.runninghi.bookmarkfolder.command.application.dto.request.CreateFolderRequest;
import com.runninghi.bookmarkfolder.command.domain.aggregate.entity.BookmarkFolder;
import com.runninghi.bookmarkfolder.command.domain.repository.BookmarkFolderRepository;
import com.runninghi.bookmarkfolder.query.application.dto.request.FindFolderRequest;
import com.runninghi.bookmarkfolder.query.application.service.FindBookmarkFolderService;
import com.runninghi.comment.command.application.dto.request.CreateCommentRequest;
import com.runninghi.comment.command.domain.aggregate.entity.Comment;
import com.runninghi.comment.command.domain.repository.CommentRepository;
import com.runninghi.feedback.command.domain.exception.customException.NotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
@Transactional
public class FindCommentsTests {

    @Autowired
    private CommentRepository commentRepository;

    @Test
    @DisplayName("조회: 댓글 전체 조회 기능 테스트")
    void testFindBookmarkFolderByNo() {
        Long userPostNo = 999L;

        commentRepository.save(Comment.builder()
                .userPostNo(userPostNo)
                .build());

        commentRepository.save(Comment.builder()
                .userPostNo(userPostNo)
                .build());

        Assertions.assertEquals(2, commentRepository.findAllByUserPostNo(userPostNo).size());

    }

    @Test
    @DisplayName("조회: 게시글 없을 시 예외처리")
    void testPostNoDoesntExist() {

        //존재하지 않는 게시물일시 NotFoundException 처리

    }

}
