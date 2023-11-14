package com.runninghi.bookmark.command.domain.service;

import com.runninghi.bookmark.command.domain.aggregate.entity.Bookmark;
import com.runninghi.bookmark.command.domain.aggregate.vo.BookmarkVO;
import com.runninghi.bookmark.command.domain.repository.BookmarkCommandRepository;
import com.runninghi.common.annotation.DomainService;
import com.runninghi.common.handler.feedback.customException.IllegalArgumentException;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@DomainService
public interface BookmarkCommandDomainService {
    void validatePostExist(Long postNo);
    void validateFolderExist(Long folderNo);

    void validateBookmarkExist(BookmarkVO bookmarkVO);
}
