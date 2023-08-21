package com.runninghi.bookmark.command.infrastructure.service;

import com.runninghi.bookmark.command.domain.service.CommandBookmarkDomainService;
import com.runninghi.bookmarkfolder.query.application.dto.request.FindFolderRequest;
import com.runninghi.bookmarkfolder.query.application.service.QueryBookmarkFolderService;
import com.runninghi.common.annotation.InfraService;
import org.springframework.beans.factory.annotation.Autowired;

@InfraService
public class CommandBookmarkInfraService implements CommandBookmarkDomainService {
    private final QueryBookmarkFolderService findBookmarkFolderService;
    @Autowired
    public CommandBookmarkInfraService(QueryBookmarkFolderService findBookmarkFolderService) {
        this.findBookmarkFolderService = findBookmarkFolderService;
    }

    @Override
    public void validatePostExist(Long postNo) {
        // 게시물 존재 확인 메소드
    }

    @Override
    public void validateFolderExist(Long folderNo) {
        findBookmarkFolderService.findBookmarkFolder(new FindFolderRequest(folderNo));
    }
}
