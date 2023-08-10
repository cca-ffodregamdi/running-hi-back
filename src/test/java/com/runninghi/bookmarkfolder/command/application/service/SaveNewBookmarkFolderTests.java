package com.runninghi.bookmarkfolder.command.application.service;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.AssertionsForClassTypes;
import com.runninghi.bookmarkfolder.command.application.dto.SaveFolderDTO;
import com.runninghi.bookmarkfolder.command.domain.repository.BookmarkFolderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
public class SaveNewBookmarkFolderTests {

    @Autowired
    private SaveNewBookmarkFolderService saveNewBookmarkFolderService;

    @Autowired
    private BookmarkFolderRepository bookmarkFolderRepository;

    @Test
    @DisplayName("즐겨찾기 폴더 추가 기능 테스트")
    void testSaveNewBookmarkFolder() {

        long beforeSize = bookmarkFolderRepository.count();

        SaveFolderDTO folderDTO = new SaveFolderDTO("testFolder", 1L);
        saveNewBookmarkFolderService.saveNewBookmarkFolder(folderDTO, folderDTO.getUserNo());

        long afterSize = bookmarkFolderRepository.count();

        org.junit.jupiter.api.Assertions.assertEquals(beforeSize + 1, afterSize);
    }

    @Test
    @DisplayName("즐겨찾기 폴더 이름 20자 이상 시 예외처리")
    void testBookmarkFolderLengthLongException() {

        SaveFolderDTO folderDTO = new SaveFolderDTO("testFoldertestFoldertestFoldertestFoldertestFolder", 1L);

        Throwable thrown = AssertionsForClassTypes.catchThrowable( ()
        -> { saveNewBookmarkFolderService.saveNewBookmarkFolder(folderDTO, folderDTO.getUserNo()); });

        Assertions.assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("폴더 제목","초과");
    }

    @Test
    @DisplayName("즐겨찾기 폴더 이름 1자 미만 시 예외처리")
    void testBookmarkFolderLengthShortException() {
        SaveFolderDTO folderDTO = new SaveFolderDTO("", 1L);

        Throwable thrown = AssertionsForClassTypes.catchThrowable( ()
                -> { saveNewBookmarkFolderService.saveNewBookmarkFolder(folderDTO, folderDTO.getUserNo()); });

        Assertions.assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("폴더 제목","미만");
    }
}
