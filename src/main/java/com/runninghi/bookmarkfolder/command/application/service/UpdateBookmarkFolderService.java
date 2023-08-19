package com.runninghi.bookmarkfolder.command.application.service;

import com.runninghi.bookmarkfolder.command.application.dto.request.UpdateFolderRequest;
import com.runninghi.bookmarkfolder.command.domain.aggregate.entity.BookmarkFolder;
import com.runninghi.bookmarkfolder.command.domain.repository.BookmarkFolderRepository;
import com.runninghi.bookmarkfolder.command.domain.service.BookmarkFolderDomainService;
import com.runninghi.bookmarkfolder.command.infrastructure.service.BookmarkFolderInfraService;
import com.runninghi.feedback.command.domain.exception.customException.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateBookmarkFolderService {

    private final BookmarkFolderRepository folderRepository;
    private final BookmarkFolderDomainService domainService;

    @Transactional
    public BookmarkFolder updateBookmarkFolder(UpdateFolderRequest folderDTO) {

        domainService.validateFolderExist(folderDTO.folderNo());
        domainService.validateFolderName(folderDTO.folderName());

        return folderRepository.save(BookmarkFolder.builder()
                .folderName(folderDTO.folderName())
                .userNo(folderDTO.userNo())
                .folderNo(folderDTO.folderNo())
                .build());

    }
}
