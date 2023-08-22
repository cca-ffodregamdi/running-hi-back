package com.runninghi.bookmarkfolder.command.application.service;

import com.runninghi.bookmark.command.application.service.BookmarkCommandService;
import com.runninghi.bookmark.command.domain.aggregate.entity.Bookmark;
import com.runninghi.bookmark.command.domain.aggregate.vo.BookmarkVO;
import com.runninghi.bookmark.command.domain.repository.BookmarkRepository;
import com.runninghi.bookmark.query.application.dto.FindBookmarkListRequest;
import com.runninghi.bookmark.query.application.dto.FindBookmarkRequest;
import com.runninghi.bookmark.query.application.service.BookmarkQueryService;
import com.runninghi.bookmarkfolder.command.application.dto.request.CreateFolderRequest;
import com.runninghi.bookmarkfolder.command.application.dto.request.DeleteFolderRequest;
import com.runninghi.bookmarkfolder.command.application.dto.request.UpdateFolderRequest;
import com.runninghi.bookmarkfolder.command.domain.aggregate.entity.BookmarkFolder;
import com.runninghi.bookmarkfolder.command.domain.repository.BookmarkFolderRepository;
import com.runninghi.bookmarkfolder.query.application.dto.request.FindFolderRequest;
import com.runninghi.bookmarkfolder.query.application.service.BookmarkFolderQueryService;
import com.runninghi.common.handler.feedback.customException.NotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.UUID;


@SpringBootTest
@Transactional
public class BookmarkFolderCommandServiceTests {

    @Autowired
    private BookmarkFolderCommandService commandBookmarkFolderService;

    @Autowired
    private BookmarkFolderQueryService bookmarkFolderQueryService;

    @Autowired
    private BookmarkQueryService bookmarkQueryService;

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private BookmarkFolderRepository bookmarkFolderRepository;


    @Test
    @DisplayName("즐겨찾기 폴더 추가 테스트 : success")
    void testCreateNewBookmarkFolder() {

        long beforeSize = bookmarkFolderRepository.count();

        CreateFolderRequest folderDTO = new CreateFolderRequest("testFolder", UUID.randomUUID());
        commandBookmarkFolderService.createNewBookmarkFolder(folderDTO);

        long afterSize = bookmarkFolderRepository.count();

        org.junit.jupiter.api.Assertions.assertEquals(beforeSize + 1, afterSize);
    }

    @Test
    @DisplayName("즐겨찾기 폴더 추가 테스트: 폴더 이름 20자 초과 시 예외처리")
    void testBookmarkFolderLengthLongException() {

        CreateFolderRequest folderDTO = new CreateFolderRequest("testFoldertestFoldertestFoldertestFoldertestFolder", UUID.randomUUID());

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> commandBookmarkFolderService.createNewBookmarkFolder(folderDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("폴더 제목이 20자를 초과하였습니다.");
    }

    @Test
    @DisplayName("즐겨찾기 폴더 추가 테스트: 폴더 이름 1자 미만 시 예외처리")
    void testBookmarkFolderLengthShortException() {
        CreateFolderRequest folderDTO = new CreateFolderRequest("", UUID.randomUUID());

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> commandBookmarkFolderService.createNewBookmarkFolder(folderDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("폴더 제목이 1자 미만입니다.");
    }

    private BookmarkFolder createBookmarkFolder() {
        return bookmarkFolderRepository.save(BookmarkFolder.builder()
                .folderName("updateTestFolder")
                .userNo(UUID.randomUUID())
                .folderNo(1L)
                .build());
    }

    @Test
    @DisplayName("즐겨찾기 폴더 수정 테스트 : success")
    void testUpdateBookmarkFolder() {

        BookmarkFolder folder = createBookmarkFolder();

        UpdateFolderRequest updateFolder = new UpdateFolderRequest(folder.getFolderNo(), "updated",folder.getUserNo());

        BookmarkFolder update = commandBookmarkFolderService.updateBookmarkFolder(updateFolder);

        BookmarkFolder bookmarkFolder = bookmarkFolderQueryService.findBookmarkFolder(new FindFolderRequest(folder.getFolderNo()));

        Assertions.assertEquals(bookmarkFolder, update);
    }

    @Test
    @DisplayName("즐겨찾기 폴더 수정 테스트: 즐겨찾기 폴더번호 없을 시 예외처리")
    void testUpdateFolderDoesntExist() {

        UpdateFolderRequest updateFolder = new UpdateFolderRequest(0L, "NotFound",UUID.randomUUID());

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> commandBookmarkFolderService.updateBookmarkFolder(updateFolder))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 폴더입니다.");

    }


    @Test
    @DisplayName("즐겨찾기 폴더 수정 테스트: 폴더 이름 1자 미만 시 예외처리")
    void testUpdateFolderLengthShortException() {

        BookmarkFolder folder = createBookmarkFolder();

        UpdateFolderRequest updateFolder = new UpdateFolderRequest(folder.getFolderNo(), "",folder.getUserNo());

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> commandBookmarkFolderService.updateBookmarkFolder(updateFolder))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("폴더 제목이 1자 미만입니다.");
    }

    @Test
    @DisplayName("즐겨찾기 폴더 수정 테스트: 폴더 이름 20자 초과 시 예외처리")
    void testUpdateFolderLengthLongException() {

        BookmarkFolder folder = createBookmarkFolder();

        UpdateFolderRequest updateFolder = new UpdateFolderRequest(folder.getFolderNo(), "testFoldertestFoldertestFoldertestFoldertestFolder",folder.getUserNo());

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> commandBookmarkFolderService.updateBookmarkFolder(updateFolder))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("폴더 제목이 20자를 초과하였습니다.");
    }

    @Test
    @DisplayName("즐겨찾기 폴더 수정 테스트: 폴더 이름이 공백 일 때 예외처리")
    void testUpdateFolderNameIsBlank() {

        BookmarkFolder folder = createBookmarkFolder();

        UpdateFolderRequest updateFolder = new UpdateFolderRequest(folder.getFolderNo(), "             ",folder.getUserNo());

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> commandBookmarkFolderService.updateBookmarkFolder(updateFolder))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("폴더 제목은 공백일 수 없습니다.");
    }

    @Test
    @DisplayName("즐겨찾기 폴더 삭제 테스트 : success - 폴더 삭제")
    void testDeleteBookmarkFolder() {

        BookmarkFolder folder = bookmarkFolderRepository.save(BookmarkFolder.builder()
                .folderName("deleteTestFolder")
                .userNo(UUID.randomUUID())
                .folderNo(1L)
                .build());

        DeleteFolderRequest folderRequest = new DeleteFolderRequest(folder.getFolderNo());
        FindFolderRequest findRequest = new FindFolderRequest(folder.getFolderNo());

        commandBookmarkFolderService.deleteBookmarkFolder(folderRequest);

        Assertions.assertThrows(NotFoundException.class, () -> {
            bookmarkFolderQueryService.findBookmarkFolder(findRequest);
        });
    }

    @Test
    @DisplayName("즐겨찾기 폴더 삭제 테스트 : success - 폴더 내 즐겨찾기 목록 삭제")
    void testDeleteBookmarksInFolder() {

        BookmarkFolder folder = bookmarkFolderRepository.save(BookmarkFolder.builder()
                .folderName("deleteTestFolder")
                .userNo(UUID.randomUUID())
                .folderNo(1L)
                .build());

        Bookmark bookmark = bookmarkRepository.save(Bookmark.builder()
                .bookmarkVO(new BookmarkVO(folder.getFolderNo(), 3L))
                .userNo(UUID.randomUUID())
                .addDate(LocalDate.now())
                .build());


        DeleteFolderRequest folderRequest = new DeleteFolderRequest(folder.getFolderNo());

        commandBookmarkFolderService.deleteBookmarkFolder(folderRequest);

        Assertions.assertThrows(NotFoundException.class, () -> {
            bookmarkQueryService.findBookmark(new FindBookmarkRequest(bookmark.getBookmarkVO()));
        });
    }

    @Test
    @DisplayName("즐겨찾기 폴더 삭제 테스트: 즐겨찾기 폴더번호 없을 시 예외처리")
    public void testDeleteFolderNoDoesntExist() {

        DeleteFolderRequest folderRequest = new DeleteFolderRequest(0L);

        Assertions.assertThrows(NotFoundException.class, () -> {
            commandBookmarkFolderService.deleteBookmarkFolder(folderRequest);
        });

    }
}
