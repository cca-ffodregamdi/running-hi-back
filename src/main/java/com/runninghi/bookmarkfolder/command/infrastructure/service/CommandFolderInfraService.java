package com.runninghi.bookmarkfolder.command.infrastructure.service;

import com.runninghi.bookmarkfolder.command.domain.repository.BookmarkFolderRepository;
import com.runninghi.bookmarkfolder.command.domain.service.CommandFolderDomainService;
import com.runninghi.common.annotation.InfraService;
import com.runninghi.common.handler.feedback.customException.NotFoundException;

@InfraService
public class CommandFolderInfraService implements CommandFolderDomainService {
    BookmarkFolderRepository folderRepository;

    public CommandFolderInfraService(BookmarkFolderRepository folderRepository) {
        this.folderRepository = folderRepository;
    }
    @Override
    public void validateFolderExist(Long folderNo) {
        folderRepository.findById(folderNo)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 폴더입니다."));
    }

    @Override
    public void validateFolderName(String folderName) {
        if (folderName.length() > 20) {
            throw new IllegalArgumentException("폴더 제목이 20자를 초과하였습니다.");
        } else if (folderName.length() < 1) {
            throw new IllegalArgumentException("폴더 제목이 1자 미만입니다.");
        }
    }

}
