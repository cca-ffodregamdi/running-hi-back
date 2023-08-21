package com.runninghi.bookmarkfolder.command.domain.service;

import com.runninghi.common.annotation.DomainService;

@DomainService
public interface CommandFolderDomainService {

    void validateFolderExist(Long folderNo);
    void validateFolderName(String folderName);

}
