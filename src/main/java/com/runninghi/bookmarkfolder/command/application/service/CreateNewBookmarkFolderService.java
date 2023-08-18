package com.runninghi.bookmarkfolder.command.application.service;

import com.runninghi.bookmarkfolder.command.application.dto.request.CreateFolderRequest;
import com.runninghi.bookmarkfolder.command.domain.aggregate.entity.BookmarkFolder;
import com.runninghi.bookmarkfolder.command.domain.repository.BookmarkFolderRepository;
import com.runninghi.bookmarkfolder.command.domain.service.BookmarkFolderDomainService;
import com.runninghi.common.annotation.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
public class CreateNewBookmarkFolderService implements BookmarkFolderDomainService {

    private final BookmarkFolderRepository folderRepository;

    @Transactional
    public BookmarkFolder createNewBookmarkFolder(CreateFolderRequest folderDTO, UUID userNo) {
        //userNo 받아옴

        if (folderDTO.getFolderName().length() > 20) {
            throw new IllegalArgumentException("폴더 제목이 20자를 초과하였습니다.");
        } else if (folderDTO.getFolderName().length() < 1) {
            throw new IllegalArgumentException("폴더 제목이 1자 미만입니다.");
        } else {
            return folderRepository.save(BookmarkFolder.builder().
                    folderName(folderDTO.folderName()).
                    userNo(userNo).
                    build());
        }
    }


}
