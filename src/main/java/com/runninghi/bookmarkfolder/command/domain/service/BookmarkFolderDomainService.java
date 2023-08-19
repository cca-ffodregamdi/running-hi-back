package com.runninghi.bookmarkfolder.command.domain.service;

import com.runninghi.bookmarkfolder.command.application.dto.request.CreateFolderRequest;
import com.runninghi.bookmarkfolder.command.domain.aggregate.entity.BookmarkFolder;
import com.runninghi.common.annotation.DomainService;
import org.springframework.transaction.annotation.Transactional;

@DomainService
public interface BookmarkFolderDomainService {

    void validateFolderExist(Long folderNo);
    void validateFolderName(String folderName);

}
