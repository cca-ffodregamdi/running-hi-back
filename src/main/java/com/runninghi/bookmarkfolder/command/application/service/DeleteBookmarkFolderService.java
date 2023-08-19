package com.runninghi.bookmarkfolder.command.application.service;

import com.runninghi.bookmarkfolder.command.application.dto.request.DeleteFolderRequest;
import com.runninghi.bookmarkfolder.command.domain.aggregate.entity.BookmarkFolder;
import com.runninghi.bookmarkfolder.command.domain.repository.BookmarkFolderRepository;
import com.runninghi.bookmarkfolder.command.domain.service.BookmarkFolderDomainService;
import com.runninghi.bookmarkfolder.command.infrastructure.service.BookmarkFolderInfraService;
import com.runninghi.common.annotation.DomainService;
import com.runninghi.feedback.command.domain.exception.customException.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DeleteBookmarkFolderService {

    private final BookmarkFolderRepository folderRepository;
    private final BookmarkFolderDomainService domainService;

    @Transactional
    public void deleteBookmarkFolder(DeleteFolderRequest folderDTO) {

        domainService.validateFolderExist(folderDTO.folderNo());
        folderRepository.deleteById(folderDTO.folderNo());

    }
}
