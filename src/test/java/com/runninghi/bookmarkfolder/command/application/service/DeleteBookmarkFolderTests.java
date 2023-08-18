package com.runninghi.bookmarkfolder.command.application.service;

import com.runninghi.bookmarkfolder.command.application.dto.request.DeleteFolderRequest;
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

import java.util.UUID;

@SpringBootTest
@Transactional
public class DeleteBookmarkFolderTests {

    private final DeleteBookmarkFolderService deleteBookmarkFolder;

    @Autowired
    BookmarkFolderRepository folderRepository;

    @Autowired
    FindBookmarkFolderService findBookmarkFolder;

    public DeleteBookmarkFolderTests(DeleteBookmarkFolderService deleteBookmarkFolder) {
        this.deleteBookmarkFolder = deleteBookmarkFolder;
    }

    @Test
    @DisplayName("즐겨찾기 폴더 삭제 기능 테스트")
    void testDeleteBookmarkFolder() {

        BookmarkFolder folder = folderRepository.save(BookmarkFolder.builder()
                .folderName("deleteTestFolder")
                .userNo(UUID.randomUUID())
                .folderNo(1L)
                .build());

        DeleteFolderRequest folderRequest = new DeleteFolderRequest(folder.getFolderNo());
        FindFolderRequest findRequest = new FindFolderRequest(folder.getFolderNo());

        deleteBookmarkFolder.deleteBookmarkFolder(folderRequest);

        Assertions.assertThrows(NotFoundException.class, () -> {
            findBookmarkFolder.findBookmarkFolder(findRequest);
        });
    }

    @Test
    @DisplayName("삭제: 즐겨찾기 폴더번호 없을 시 예외처리")
    public void testDeleteFolderNoDoesntExist() {

        DeleteFolderRequest folderRequest = new DeleteFolderRequest(0L);

        Assertions.assertThrows(NotFoundException.class, () -> {
            deleteBookmarkFolder.deleteBookmarkFolder(folderRequest);
        });

    }
}
