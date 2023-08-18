package com.runninghi.bookmarkfolder.query.application.service;

import com.runninghi.bookmarkfolder.command.application.dto.SaveFolderDTO;
import com.runninghi.bookmarkfolder.command.application.service.SaveNewBookmarkFolderService;
import com.runninghi.bookmarkfolder.command.domain.aggregate.entity.BookmarkFolder;
import com.runninghi.bookmarkfolder.query.infrastructure.repository.FindFolderRepository;
import com.runninghi.feedback.command.domain.exception.customException.NotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
@Transactional
public class FindBookmarkFolderTests {

    @Autowired
    private FindBookmarkFolderImpl findBookmarkFolderImpl;

    @Autowired
    private SaveNewBookmarkFolderService saveNewBookmarkFolderService;

    @Autowired
    private FindFolderRepository findFolderRepository;

    @Test
    @DisplayName("즐겨찾기 폴더 조회 기능 테스트")
    void testFindBookmarkFolderByNo() {
        SaveFolderDTO folderDTO = new SaveFolderDTO("testFind", 1L);
        BookmarkFolder folder = saveNewBookmarkFolderService.saveNewBookmarkFolder(folderDTO, 1L);

        long folderNo = folder.getFolderNo();

        findBookmarkFolderImpl.findBookmarkFolder(folderNo).ifPresent(actualFolder -> {
            Assertions.assertEquals(folderDTO.getFolderName(), actualFolder.getFolderName());
            Assertions.assertEquals(folderDTO.getUserNo(), actualFolder.getUserNo());
        });
    }

    @Test
    @DisplayName("즐겨찾기 폴더번호 없을 시 예외처리")
    void testFolderNoDoesntExist() {
        long folderNo = 0L;

        Assertions.assertThrows(NotFoundException.class, () -> {
            findBookmarkFolderImpl.findBookmarkFolder(folderNo);
        });
    }
}
