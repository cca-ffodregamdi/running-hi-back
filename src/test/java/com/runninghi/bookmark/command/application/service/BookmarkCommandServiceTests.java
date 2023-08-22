package com.runninghi.bookmark.command.application.service;

import com.runninghi.bookmark.command.application.dto.request.CreateBookmarkRequest;
import com.runninghi.bookmark.command.application.dto.request.DeleteBookmarkRequest;
import com.runninghi.bookmark.command.domain.aggregate.vo.BookmarkVO;
import com.runninghi.bookmark.command.domain.repository.BookmarkRepository;
import com.runninghi.bookmark.query.application.dto.FindBookmarkRequest;
import com.runninghi.bookmark.query.application.service.BookmarkQueryService;
import com.runninghi.common.handler.feedback.customException.NotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;


@SpringBootTest
@Transactional
public class BookmarkCommandServiceTests {

    @Autowired
    private BookmarkCommandService commandBookmarkService;

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private BookmarkQueryService queryBookmarkService;


    @Test
    @DisplayName("즐겨찾기 추가 테스트 : success")
    void testCreateBookmark() {

        Long beforeSize = bookmarkRepository.count();

        BookmarkVO bookmarkVO = new BookmarkVO(1L, 2L);
        CreateBookmarkRequest bookmarkRequest = new CreateBookmarkRequest(bookmarkVO, UUID.randomUUID());
        commandBookmarkService.createBookmark(bookmarkRequest);

        Long afterSize = bookmarkRepository.count();

        Assertions.assertEquals(beforeSize + 1, afterSize);

    }

    @Test
    @DisplayName("즐겨찾기 추가 테스트: 존재하지 않는 즐겨찾기 폴더 번호 예외처리")
    void testFolderNoDoesntExist() {

        BookmarkVO bookmarkVO = new BookmarkVO(0L, 1L);
        CreateBookmarkRequest bookmarkRequest = new CreateBookmarkRequest(bookmarkVO, UUID.randomUUID());

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> commandBookmarkService.createBookmark(bookmarkRequest))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("해당 폴더가 존재하지 않습니다.");
    }

    @Test
    @DisplayName("즐겨찾기 추가 테스트: 존재하지 않는 게시물 번호 예외처리")
    void testPostNoDoesntExist() {

//        BookmarkVO bookmarkVO = new BookmarkVO(1L, 0L);
//        CreateBookmarkRequest bookmarkRequest = new CreateBookmarkRequest(bookmarkVO, UUID.randomUUID());
//
//        org.assertj.core.api.Assertions.assertThatThrownBy(() -> createBookmarkService.createBookmark(bookmarkRequest))
//                .isInstanceOf(NotFoundException.class)
//                .hasMessage("존재하지 않는 게시물입니다.");
    }

    @Test
    @DisplayName("즐겨찾기 삭제 테스트 : success")
    void testDeleteBookmark() {
        BookmarkVO bookmarkVO = new BookmarkVO(1L, 2L);
        CreateBookmarkRequest createRequest = new CreateBookmarkRequest(bookmarkVO, UUID.randomUUID());
        commandBookmarkService.createBookmark(createRequest);

        DeleteBookmarkRequest deleteRequest = new DeleteBookmarkRequest(new BookmarkVO(bookmarkVO.getFolderNo(), bookmarkVO.getPostNo()));
        commandBookmarkService.deleteBookmark(deleteRequest);

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> queryBookmarkService.findBookmark(new FindBookmarkRequest(bookmarkVO)))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 즐겨찾기 입니다.");

    }

    @Test
    @DisplayName("즐겨찾기 삭제 테스트: 즐겨찾기 폴더번호 없을 시 예외처리")
    public void testDeleteFolderNoDoesntExist() {

        DeleteBookmarkRequest deleteRequest = new DeleteBookmarkRequest(new BookmarkVO(0L, 1L));

        Assertions.assertThrows(NotFoundException.class, () -> {
            commandBookmarkService.deleteBookmark(deleteRequest);
        });

    }


}
