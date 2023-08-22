package com.runninghi.bookmarkfolder.command.domain.service;

import com.runninghi.common.annotation.DomainService;

public interface FolderCommandDomainService {

    void validateFolderExist(Long folderNo);
    void validateFolderName(String folderName);

}
