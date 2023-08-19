package com.runninghi.bookmark.command.application.service;

import com.runninghi.bookmark.command.application.dto.request.CreateBookmarkRequest;
import com.runninghi.bookmark.command.application.dto.request.DeleteBookmarkRequest;
import com.runninghi.bookmark.command.domain.aggregate.vo.BookmarkVO;
import com.runninghi.bookmark.command.domain.repository.BookmarkRepository;
import com.runninghi.bookmark.query.application.dto.FindBookmarkRequest;
import com.runninghi.bookmark.query.application.service.FindBookmarkService;
import com.runninghi.bookmarkfolder.command.application.dto.request.DeleteFolderRequest;
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
public class DeleteBookmarkTests {

    @Autowired
    CreateBookmarkService createBookmarkService;

    @Autowired
    BookmarkRepository bookmarkRepository;

    @Autowired
    DeleteBookmarkService deleteBookmarkService;

    @Autowired
    FindBookmarkService findBookmarkService;

    @Test
    @DisplayName("삭제: 즐겨찾기 삭제 기능")
    void testDeleteBookmark() {
        BookmarkVO bookmarkVO = new BookmarkVO(1L, 2L);
        CreateBookmarkRequest createRequest = new CreateBookmarkRequest(bookmarkVO, UUID.randomUUID());
        createBookmarkService.createBookmark(createRequest);

        DeleteBookmarkRequest deleteRequest = new DeleteBookmarkRequest(new BookmarkVO(bookmarkVO.getFolderNo(), bookmarkVO.getPostNo()));
        deleteBookmarkService.deleteBookmark(deleteRequest);

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> findBookmarkService.findBookmark(new FindBookmarkRequest(bookmarkVO)))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 즐겨찾기 입니다.");

    }

    @Test
    @DisplayName("삭제: 즐겨찾기 폴더번호 없을 시 예외처리")
    public void testDeleteFolderNoDoesntExist() {

        DeleteBookmarkRequest deleteRequest = new DeleteBookmarkRequest(new BookmarkVO(0L, 1L));

        Assertions.assertThrows(NotFoundException.class, () -> {
            deleteBookmarkService.deleteBookmark(deleteRequest);
        });

    }

}
