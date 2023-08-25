package com.runninghi.bookmark.command.infrastructure.service;

import com.runninghi.bookmark.command.domain.aggregate.entity.Bookmark;
import com.runninghi.bookmark.command.domain.aggregate.vo.BookmarkVO;
import com.runninghi.bookmark.command.domain.repository.BookmarkCommandRepository;
import com.runninghi.bookmark.command.domain.service.BookmarkCommandDomainService;
import com.runninghi.bookmarkfolder.query.application.dto.request.FindFolderRequest;
import com.runninghi.bookmarkfolder.query.application.service.BookmarkFolderQueryService;
import com.runninghi.common.annotation.InfraService;
import com.runninghi.common.handler.feedback.customException.IllegalArgumentException;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@InfraService
@RequiredArgsConstructor
public class BookmarkCommandInfraService implements BookmarkCommandDomainService {
    private final BookmarkFolderQueryService findBookmarkFolderService;
    private final BookmarkCommandRepository bookmarkRepository;

    @Override
    public void validatePostExist(Long postNo) {
        // 게시물 존재 확인 메소드
    }

    @Override
    public void validateFolderExist(Long folderNo) {
        findBookmarkFolderService.findBookmarkFolder(new FindFolderRequest(folderNo));
    }

    @Override
    public void validateBookmarkExist(BookmarkVO bookmarkVO) {
        Optional<Bookmark> bookmark = bookmarkRepository.findById(bookmarkVO);
        if(bookmark.isPresent()) {
            throw new IllegalArgumentException("이미 저장되어있는 즐겨찾기 입니다.");
        }
    }
}
