package com.runninghi.bookmarkfolder.query.application.service;

import com.runninghi.bookmarkfolder.command.application.dto.request.CreateFolderRequest;
import com.runninghi.bookmarkfolder.command.domain.aggregate.entity.BookmarkFolder;
import com.runninghi.bookmarkfolder.command.domain.repository.BookmarkFolderRepository;
import com.runninghi.bookmarkfolder.query.application.dto.request.FindFolderRequest;
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
public class BookmarkFolderQueryServiceTests {

    @Autowired
    private BookmarkFolderQueryService findBookmarkFolder;

    @Autowired
    private BookmarkFolderRepository folderRepository;

    @Test
    @DisplayName("즐겨찾기 폴더 조회 테스트 : success")
    void testFindBookmarkFolderByNo() {
        CreateFolderRequest folderDTO = new CreateFolderRequest("testFind", UUID.randomUUID());

        BookmarkFolder folder = folderRepository.save(BookmarkFolder.builder()
                .folderName(folderDTO.folderName())
                .userNo(folderDTO.getUserNo())
                .folderNo(1L)
                .build());

        FindFolderRequest folderRequest = new FindFolderRequest(folder.getFolderNo());

        BookmarkFolder findFolder = findBookmarkFolder.findBookmarkFolder(folderRequest);

        Assertions.assertEquals(findFolder, folder);
    }

    @Test
    @DisplayName("즐겨찾기 폴더 조회 테스트: 즐겨찾기 폴더번호 없을 시 예외처리")
    void testFindFolderNoDoesntExist() {
        FindFolderRequest folderRequest = new FindFolderRequest(0L);

        Assertions.assertThrows(NotFoundException.class, () -> {
            findBookmarkFolder.findBookmarkFolder(folderRequest);
        });
    }
}
