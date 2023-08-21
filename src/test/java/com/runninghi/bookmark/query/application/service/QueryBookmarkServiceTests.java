package com.runninghi.bookmark.query.application.service;

import com.runninghi.bookmark.command.application.dto.request.CreateBookmarkRequest;
import com.runninghi.bookmark.command.application.service.CommandBookmarkService;
import com.runninghi.bookmark.command.domain.aggregate.entity.Bookmark;
import com.runninghi.bookmark.command.domain.aggregate.vo.BookmarkVO;
import com.runninghi.bookmark.query.application.dto.FindBookmarkRequest;
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
public class QueryBookmarkServiceTests {

    @Autowired
    CommandBookmarkService createBookmarkService;

    @Autowired
    QueryBookmarkService queryBookmarkService;

    @Test
    @DisplayName("즐겨찾기 조회 테스트 : success")
    void testFindBookmark() {

        BookmarkVO bookmarkVO = new BookmarkVO(1L, 2L);
        CreateBookmarkRequest bookmarkRequest = new CreateBookmarkRequest(bookmarkVO, UUID.randomUUID());
        Bookmark bookmark = createBookmarkService.createBookmark(bookmarkRequest);

        FindBookmarkRequest findRequest = new FindBookmarkRequest(bookmarkVO);

        Assertions.assertEquals(bookmark, queryBookmarkService.findBookmark(findRequest));
    }

    @Test
    @DisplayName("즐겨찾기 조회 테스트: 폴더 번호가 없을 시 예외발생")
    void testFolderNoDoesntExist() {

        BookmarkVO bookmarkVO = new BookmarkVO(0L, 2L);
        FindBookmarkRequest findRequest = new FindBookmarkRequest(bookmarkVO);

        Assertions.assertThrows(NotFoundException.class, () -> {
            queryBookmarkService.findBookmark(findRequest);
        });
    }



}
