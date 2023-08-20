package com.runninghi.bookmark.command.infrastructure.service;

import com.runninghi.bookmark.command.domain.service.BookmarkDomainService;
import com.runninghi.bookmarkfolder.command.domain.repository.BookmarkFolderRepository;
import com.runninghi.bookmarkfolder.query.application.dto.request.FindFolderRequest;
import com.runninghi.bookmarkfolder.query.application.service.FindBookmarkFolderService;
import com.runninghi.common.annotation.InfraService;
import com.runninghi.feedback.command.domain.exception.customException.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

@InfraService
public class BookmarkInfraService implements BookmarkDomainService {
    private final FindBookmarkFolderService findBookmarkFolderService;
    @Autowired
    public BookmarkInfraService(FindBookmarkFolderService findBookmarkFolderService) {
        this.findBookmarkFolderService = findBookmarkFolderService;
    }

    @Override
    public void validatePostExist(Long postNo) {
        // 게시물 존재 확인 메소드
    }

    @Override
    public void validateFolderExist(Long folderNo) {
        findBookmarkFolderService.findBookmarkFolder(new FindFolderRequest(folderNo))
                .orElseThrow(() -> new NotFoundException("해당 폴더가 존재하지 않습니다."));
    }
}
