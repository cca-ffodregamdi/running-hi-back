package com.runninghi.bookmark.query.application.service;

import com.runninghi.bookmark.command.application.dto.request.CreateBookmarkRequest;
import com.runninghi.bookmark.command.application.service.CreateBookmarkService;
import com.runninghi.bookmark.command.domain.aggregate.entity.Bookmark;
import com.runninghi.bookmark.command.domain.aggregate.vo.BookmarkVO;
import com.runninghi.bookmark.command.domain.repository.BookmarkRepository;
import com.runninghi.bookmark.query.application.dto.FindBookmarkRequest;
import com.runninghi.bookmarkfolder.command.application.service.CreateNewBookmarkFolderService;
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
public class FindBookmarkTests {

    @Autowired
    CreateBookmarkService createBookmarkService;

    @Autowired
    FindBookmarkService findBookmarkService;

    @Test
    @DisplayName("즐겨찾기 조회 기능 테스트")
    void testFindBookmark() {

        BookmarkVO bookmarkVO = new BookmarkVO(1L, 2L);
        CreateBookmarkRequest bookmarkRequest = new CreateBookmarkRequest(bookmarkVO, UUID.randomUUID());
        Bookmark bookmark = createBookmarkService.createBookmark(bookmarkRequest);

        FindBookmarkRequest findRequest = new FindBookmarkRequest(bookmarkVO);

        Assertions.assertEquals(bookmark, findBookmarkService.findBookmark(findRequest));
    }



}
