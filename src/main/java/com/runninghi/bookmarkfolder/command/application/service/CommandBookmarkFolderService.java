package com.runninghi.bookmarkfolder.command.application.service;

import com.runninghi.bookmarkfolder.command.application.dto.request.CreateFolderRequest;
import com.runninghi.bookmarkfolder.command.application.dto.request.DeleteFolderRequest;
import com.runninghi.bookmarkfolder.command.application.dto.request.UpdateFolderRequest;
import com.runninghi.bookmarkfolder.command.domain.aggregate.entity.BookmarkFolder;
import com.runninghi.bookmarkfolder.command.domain.repository.BookmarkFolderRepository;
import com.runninghi.bookmarkfolder.command.domain.service.CommandFolderDomainService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommandBookmarkFolderService {

    private final BookmarkFolderRepository folderRepository;
    private final CommandFolderDomainService domainService;
    @Transactional
    public void createNewBookmarkFolder(CreateFolderRequest folderDTO) {

        domainService.validateFolderName(folderDTO.folderName());

        folderRepository.save(BookmarkFolder.builder().
                folderName(folderDTO.folderName()).
                userNo(folderDTO.getUserNo()).
                build());
    }

    @Transactional
    public void deleteBookmarkFolder(DeleteFolderRequest folderDTO) {

        domainService.validateFolderExist(folderDTO.folderNo());
        folderRepository.deleteById(folderDTO.folderNo());

    }

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
