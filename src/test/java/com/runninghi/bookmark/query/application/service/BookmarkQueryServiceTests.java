package com.runninghi.bookmark.query.application.service;

import com.runninghi.bookmark.command.application.dto.request.CreateBookmarkRequest;
import com.runninghi.bookmark.command.application.service.BookmarkCommandService;
import com.runninghi.bookmark.command.domain.aggregate.entity.Bookmark;
import com.runninghi.bookmark.command.domain.aggregate.vo.BookmarkUserVO;
import com.runninghi.bookmark.command.domain.aggregate.vo.BookmarkVO;
import com.runninghi.bookmark.command.domain.repository.BookmarkCommandRepository;
import com.runninghi.bookmark.query.application.dto.FindBookmarkListRequest;
import com.runninghi.bookmark.query.application.dto.FindBookmarkRequest;
import com.runninghi.common.handler.feedback.customException.NotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;


@SpringBootTest
@Transactional
public class BookmarkQueryServiceTests {

    @Autowired
    BookmarkCommandService createBookmarkService;

    @Autowired
    BookmarkQueryService queryBookmarkService;

    @Autowired
    BookmarkCommandRepository bookmarkRepository;

    @BeforeEach
    void clear() {
        bookmarkRepository.deleteAllInBatch();
    }


    @Test
    @DisplayName("즐겨찾기 조회 테스트 : success - 특정 즐겨찾기")
    void testFindBookmark() {

        BookmarkVO bookmarkVO = new BookmarkVO(1L, 2L);
        CreateBookmarkRequest bookmarkRequest = new CreateBookmarkRequest(bookmarkVO, new BookmarkUserVO(UUID.randomUUID()));
        Bookmark bookmark = createBookmarkService.createBookmark(bookmarkRequest);

        FindBookmarkRequest findRequest = new FindBookmarkRequest(bookmarkVO);

        Assertions.assertEquals(bookmark, queryBookmarkService.findBookmark(findRequest));
    }

    @Test
    @DisplayName("즐겨찾기 조회 테스트 : success - 폴더 속 전체 즐겨찾기 리스트")
    void testFindBookmarkList() {

        BookmarkVO bookmarkVO1 = new BookmarkVO(1L, 2L);
        BookmarkVO bookmarkVO2 = new BookmarkVO(1L, 3L);
        CreateBookmarkRequest bookmarkRequest1 = new CreateBookmarkRequest(bookmarkVO1, new BookmarkUserVO(UUID.randomUUID()));
        CreateBookmarkRequest bookmarkRequest2 = new CreateBookmarkRequest(bookmarkVO2, new BookmarkUserVO(UUID.randomUUID()));

        Bookmark bookmark1 = createBookmarkService.createBookmark(bookmarkRequest1);
        Bookmark bookmark2 = createBookmarkService.createBookmark(bookmarkRequest2);

        FindBookmarkListRequest listRequest = new FindBookmarkListRequest(1L);
        List<Bookmark> bookmarkList = queryBookmarkService.findBookmarkListByFolder(listRequest);

        org.assertj.core.api.Assertions.assertThat(bookmarkList).contains(bookmark1, bookmark2);
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
