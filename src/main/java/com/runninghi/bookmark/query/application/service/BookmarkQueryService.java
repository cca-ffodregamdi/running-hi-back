package com.runninghi.bookmark.query.application.service;

import com.runninghi.bookmark.command.domain.aggregate.entity.Bookmark;
import com.runninghi.bookmark.command.domain.repository.BookmarkCommandRepository;
import com.runninghi.bookmark.command.domain.service.BookmarkCommandDomainService;
import com.runninghi.bookmark.query.application.dto.FindBookmarkListRequest;
import com.runninghi.bookmark.query.application.dto.FindBookmarkRequest;
import com.runninghi.bookmark.query.infrastructure.repository.BookmarkQueryRepository;
import com.runninghi.common.handler.feedback.customException.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkQueryService {

    private final BookmarkQueryRepository bookmarkQueryRepository;
    private final BookmarkCommandDomainService bookmarkDomainService;
    @Transactional(readOnly=true)
    public Bookmark findBookmark(FindBookmarkRequest bookmarkDTO) {
        return bookmarkQueryRepository.findById(bookmarkDTO.bookmarkVO())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 즐겨찾기 입니다."));
    }

    @Transactional(readOnly = true)
    public List<Bookmark> findBookmarkListByFolder(FindBookmarkListRequest bookmarkDTO) {
        bookmarkDomainService.validateFolderExist(bookmarkDTO.folderNo());
        return bookmarkQueryRepository.findBookmarkByBookmarkVO_FolderNo(bookmarkDTO.folderNo());
    }
}
