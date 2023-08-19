package com.runninghi.bookmark.command.infrastructure.service;

import com.runninghi.bookmark.command.domain.repository.BookmarkRepository;
import com.runninghi.bookmark.command.domain.service.BookmarkDomainService;
import com.runninghi.bookmarkfolder.command.domain.repository.BookmarkFolderRepository;
import com.runninghi.bookmarkfolder.query.application.service.FindBookmarkFolderService;
import com.runninghi.common.annotation.DomainService;
import com.runninghi.common.annotation.InfraService;
import com.runninghi.feedback.command.domain.exception.customException.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.Annotation;

@InfraService
public class BookmarkInfraService implements BookmarkDomainService {

    @Autowired
    BookmarkFolderRepository bookmarkFolderRepository;

    @Override
    public void validatePostExist(Long postNo) {
        // 게시물 존재 확인 메소드
    }

    @Override
    public void validateFolderExist(Long folderNo) {
        bookmarkFolderRepository.findById(folderNo)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 폴더입니다."));
    }
}
