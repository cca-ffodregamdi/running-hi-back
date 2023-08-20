package com.runninghi.bookmark.command.domain.service;

import com.runninghi.common.annotation.DomainService;

@DomainService
public interface CommandBookmarkDomainService {
    void validatePostExist(Long postNo);
    void validateFolderExist(Long folderNo);
}
