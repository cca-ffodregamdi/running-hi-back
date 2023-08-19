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
    @DisplayName("생성: 즐겨찾기 폴더 추가 기능 테스트")
    void testCreateNewBookmarkFolder() {

        long beforeSize = bookmarkFolderRepository.count();

        CreateFolderRequest folderDTO = new CreateFolderRequest("testFolder", UUID.randomUUID());
        createNewBookmarkFolderService.createNewBookmarkFolder(folderDTO);

        long afterSize = bookmarkFolderRepository.count();

        org.junit.jupiter.api.Assertions.assertEquals(beforeSize + 1, afterSize);
    }

    @Test
    @DisplayName("생성: 폴더 이름 20자 초과 시 예외처리")
    void testBookmarkFolderLengthLongException() {

        CreateFolderRequest folderDTO = new CreateFolderRequest("testFoldertestFoldertestFoldertestFoldertestFolder", UUID.randomUUID());

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> createNewBookmarkFolderService.createNewBookmarkFolder(folderDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("폴더 제목이 20자를 초과하였습니다.");
    }

    @Test
    @DisplayName("생성: 폴더 이름 1자 미만 시 예외처리")
    void testBookmarkFolderLengthShortException() {
        CreateFolderRequest folderDTO = new CreateFolderRequest("", UUID.randomUUID());

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> createNewBookmarkFolderService.createNewBookmarkFolder(folderDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("폴더 제목이 1자 미만입니다.");
    }
}
