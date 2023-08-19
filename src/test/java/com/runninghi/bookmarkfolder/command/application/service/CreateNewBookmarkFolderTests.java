package com.runninghi.bookmarkfolder.command.application.service;

import com.runninghi.bookmarkfolder.command.application.dto.request.CreateFolderRequest;
import com.runninghi.bookmarkfolder.command.domain.repository.BookmarkFolderRepository;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;


@SpringBootTest
@Transactional
public class CreateNewBookmarkFolderTests {

    @Autowired
    private CreateNewBookmarkFolderService createNewBookmarkFolderService;

    @Autowired
    private BookmarkFolderRepository bookmarkFolderRepository;


    @Test
    @DisplayName("즐겨찾기 폴더 추가 기능 테스트")
    void testCreateNewBookmarkFolder() {

        long beforeSize = bookmarkFolderRepository.count();

        CreateFolderRequest folderDTO = new CreateFolderRequest("testFolder", UUID.randomUUID());
        createNewBookmarkFolderService.createNewBookmarkFolder(folderDTO, folderDTO.getUserNo());

        long afterSize = bookmarkFolderRepository.count();

        org.junit.jupiter.api.Assertions.assertEquals(beforeSize + 1, afterSize);
    }

    @Test
    @DisplayName("즐겨찾기 폴더 이름 20자 이상 시 예외처리")
    void testBookmarkFolderLengthLongException() {

        CreateFolderRequest folderDTO = new CreateFolderRequest("testFoldertestFoldertestFoldertestFoldertestFolder", UUID.randomUUID());

        Throwable thrown = AssertionsForClassTypes.catchThrowable(()
                -> {
            createNewBookmarkFolderService.createNewBookmarkFolder(folderDTO, folderDTO.getUserNo());
        });

        Assertions.assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("폴더 제목", "초과");
    }

    @Test
    @DisplayName("즐겨찾기 폴더 이름 1자 미만 시 예외처리")
    void testBookmarkFolderLengthShortException() {
        CreateFolderRequest folderDTO = new CreateFolderRequest("", UUID.randomUUID());

        Throwable thrown = AssertionsForClassTypes.catchThrowable(()
                -> {
            createNewBookmarkFolderService.createNewBookmarkFolder(folderDTO, folderDTO.getUserNo());
        });

        Assertions.assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("폴더 제목", "미만");
    }
}
