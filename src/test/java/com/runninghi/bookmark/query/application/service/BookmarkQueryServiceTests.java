package com.runninghi.bookmark.query.application.service;

import com.runninghi.bookmark.command.application.dto.request.CreateBookmarkRequest;
import com.runninghi.bookmark.command.application.dto.response.BookmarkCommandResponse;
import com.runninghi.bookmark.command.application.service.BookmarkCommandService;
import com.runninghi.bookmark.command.domain.aggregate.entity.Bookmark;
import com.runninghi.bookmark.command.domain.aggregate.vo.BookmarkVO;
import com.runninghi.bookmark.command.domain.repository.BookmarkCommandRepository;
import com.runninghi.bookmark.query.application.dto.FindBookmarkListRequest;
import com.runninghi.bookmark.query.application.dto.FindBookmarkRequest;
import com.runninghi.bookmarkfolder.command.domain.aggregate.entity.BookmarkFolder;
import com.runninghi.bookmarkfolder.command.domain.aggregate.vo.FolderMemberVO;
import com.runninghi.bookmarkfolder.command.domain.repository.FolderCommandRepository;
import com.runninghi.common.handler.feedback.customException.NotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
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

    @Autowired
    FolderCommandRepository folderCommandRepository;

    @BeforeEach
    @AfterEach
    void clear() {
        bookmarkRepository.deleteAllInBatch();
        folderCommandRepository.deleteAllInBatch();
    }


    @Test
    @DisplayName("즐겨찾기 조회 테스트 : success - 특정 즐겨찾기")
    void testFindBookmark() {

        BookmarkFolder folder = folderCommandRepository.save(BookmarkFolder.builder()
                .folderNo(999L)
                .folderName("폴더 수정")
                .memberNoVO(new FolderMemberVO(UUID.randomUUID()))
                .build());

        BookmarkVO bookmarkVO = new BookmarkVO(folder.getFolderNo(), 2L);
        CreateBookmarkRequest bookmarkRequest = new CreateBookmarkRequest(bookmarkVO, UUID.randomUUID());
        BookmarkCommandResponse bookmark = createBookmarkService.createBookmark(bookmarkRequest);

        FindBookmarkRequest findRequest = new FindBookmarkRequest(bookmarkVO);

        Assertions.assertEquals(bookmark.bookmarkVO(), queryBookmarkService.findBookmark(findRequest).getBookmarkVO());
    }

    @Test
    @DisplayName("즐겨찾기 조회 테스트 : success - 폴더 속 전체 즐겨찾기 리스트")
    void testFindBookmarkList() {

        BookmarkFolder folder = folderCommandRepository.save(BookmarkFolder.builder()
                .folderNo(1L)
                .folderName("폴더 수정")
                .memberNoVO(new FolderMemberVO(UUID.randomUUID()))
                .build());

        BookmarkVO bookmarkVO1 = new BookmarkVO(folder.getFolderNo(), 2L);
        BookmarkVO bookmarkVO2 = new BookmarkVO(folder.getFolderNo(), 3L);
        CreateBookmarkRequest bookmarkRequest1 = new CreateBookmarkRequest(bookmarkVO1, UUID.randomUUID());
        CreateBookmarkRequest bookmarkRequest2 = new CreateBookmarkRequest(bookmarkVO2, UUID.randomUUID());

        BookmarkCommandResponse bookmark1 = createBookmarkService.createBookmark(bookmarkRequest1);
        BookmarkCommandResponse bookmark2 = createBookmarkService.createBookmark(bookmarkRequest2);

        FindBookmarkListRequest listRequest = new FindBookmarkListRequest(folder.getFolderNo());
        List<Bookmark> bookmarkList = queryBookmarkService.findBookmarkListByFolder(listRequest);

        Assertions.assertEquals(bookmarkList.get(0).getBookmarkVO(), bookmark1.bookmarkVO());
        Assertions.assertEquals(bookmarkList.get(1).getBookmarkVO(), bookmark2.bookmarkVO());
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
