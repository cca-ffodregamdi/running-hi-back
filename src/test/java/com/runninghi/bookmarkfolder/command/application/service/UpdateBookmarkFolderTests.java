package com.runninghi.bookmarkfolder.command.application.service;

import com.runninghi.bookmarkfolder.command.application.dto.request.CreateFolderRequest;
import com.runninghi.bookmarkfolder.command.application.dto.request.UpdateFolderRequest;
import com.runninghi.bookmarkfolder.command.domain.aggregate.entity.BookmarkFolder;
import com.runninghi.bookmarkfolder.command.domain.repository.BookmarkFolderRepository;
import com.runninghi.bookmarkfolder.query.application.dto.request.FindFolderRequest;
import com.runninghi.bookmarkfolder.query.application.service.FindBookmarkFolderService;
import com.runninghi.feedback.command.domain.exception.customException.NotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootTest
@Transactional
public class UpdateBookmarkFolderTests {

    @Autowired
    private BookmarkFolderRepository folderRepository;

    @Autowired
    UpdateBookmarkFolderService updateBookmarkFolderService;

    @Autowired
    FindBookmarkFolderService findBookmarkFolderService;

    private BookmarkFolder createBookmarkFolder() {
        return folderRepository.save(BookmarkFolder.builder()
                .folderName("updateTestFolder")
                .userNo(UUID.randomUUID())
                .folderNo(1L)
                .build());
    }

    @Test
    @DisplayName("수정: 즐겨찾기 폴더 수정 기능 테스트")
    void testCreateNewBookmarkFolder() {

        BookmarkFolder folder = createBookmarkFolder();

        UpdateFolderRequest updateFolder = new UpdateFolderRequest(folder.getFolderNo(), "updated",folder.getUserNo());

        BookmarkFolder update = updateBookmarkFolderService.updateBookmarkFolder(updateFolder);

        BookmarkFolder bookmarkFolder = findBookmarkFolderService.findBookmarkFolder(new FindFolderRequest(folder.getFolderNo()));

        Assertions.assertEquals(bookmarkFolder, update);
    }

    @Test
    @DisplayName("수정: 즐겨찾기 폴더번호 없을 시 예외처리")
    void testUpdateFolderDoesntExist() {

        UpdateFolderRequest updateFolder = new UpdateFolderRequest(0L, "NotFound",UUID.randomUUID());

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> updateBookmarkFolderService.updateBookmarkFolder(updateFolder))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 폴더입니다.");

    }


    @Test
    @DisplayName("수정: 폴더 이름 1자 미만 시 예외처리")
    void testUpdateFolderLengthShortException() {

        BookmarkFolder folder = createBookmarkFolder();

        UpdateFolderRequest updateFolder = new UpdateFolderRequest(folder.getFolderNo(), "",folder.getUserNo());

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> updateBookmarkFolderService.updateBookmarkFolder(updateFolder))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("폴더 제목이 1자 미만입니다.");
    }

    @Test
    @DisplayName("수정: 폴더 이름 20자 초과 시 예외처리")
    void testUpdateFolderLengthLongException() {

        BookmarkFolder folder = createBookmarkFolder();

        UpdateFolderRequest updateFolder = new UpdateFolderRequest(folder.getFolderNo(), "testFoldertestFoldertestFoldertestFoldertestFolder",folder.getUserNo());

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> updateBookmarkFolderService.updateBookmarkFolder(updateFolder))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("폴더 제목이 20자를 초과하였습니다.");
    }
}
