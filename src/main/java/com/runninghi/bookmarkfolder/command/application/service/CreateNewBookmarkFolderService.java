package com.runninghi.bookmarkfolder.command.application.service;

import com.runninghi.bookmarkfolder.command.application.dto.request.CreateFolderRequest;
import com.runninghi.bookmarkfolder.command.domain.aggregate.entity.BookmarkFolder;
import com.runninghi.bookmarkfolder.command.domain.repository.BookmarkFolderRepository;
import com.runninghi.bookmarkfolder.command.infrastructure.service.BookmarkFolderInfraService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateNewBookmarkFolderService {

    private final BookmarkFolderRepository folderRepository;
    private final BookmarkFolderInfraService infraService;
    public void createNewBookmarkFolder(CreateFolderRequest folderDTO) {

        infraService.validateFolderName(folderDTO.folderName());

        folderRepository.save(BookmarkFolder.builder().
                folderName(folderDTO.folderName()).
                userNo(folderDTO.getUserNo()).
                build());
    }

}
