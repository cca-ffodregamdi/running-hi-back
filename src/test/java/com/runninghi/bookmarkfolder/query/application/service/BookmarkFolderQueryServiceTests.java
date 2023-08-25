package com.runninghi.bookmarkfolder.query.application.service;

import com.runninghi.bookmarkfolder.command.application.dto.request.CreateFolderRequest;
import com.runninghi.bookmarkfolder.command.domain.aggregate.entity.BookmarkFolder;
import com.runninghi.bookmarkfolder.command.domain.aggregate.vo.FolderUserVO;
import com.runninghi.bookmarkfolder.command.domain.repository.FolderCommandRepository;
import com.runninghi.bookmarkfolder.query.application.dto.request.FindFolderRequest;
import com.runninghi.bookmarkfolder.query.application.dto.response.FolderQueryResponse;
import com.runninghi.common.handler.feedback.customException.NotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
    private FolderCommandRepository folderRepository;

    @BeforeEach
    void clear() {
        folderRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("즐겨찾기 폴더 조회 테스트 : success")
    void testFindBookmarkFolderByNo() {
        CreateFolderRequest folderDTO = new CreateFolderRequest("testFind", new FolderUserVO(UUID.randomUUID()));

        BookmarkFolder folder = folderRepository.save(BookmarkFolder.builder()
                .folderName(folderDTO.folderName())
                .userNo(folderDTO.getUserNo())
                .folderNo(1L)
                .build());

        FindFolderRequest folderRequest = new FindFolderRequest(folder.getFolderNo());

        FolderQueryResponse findFolder = findBookmarkFolder.findBookmarkFolder(folderRequest);

        Assertions.assertEquals(findFolder.folderName(), folder.getFolderName());
        Assertions.assertEquals(findFolder.folderNo(), folder.getFolderNo());
        Assertions.assertEquals(findFolder.userNo(), folder.getUserNo());
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
