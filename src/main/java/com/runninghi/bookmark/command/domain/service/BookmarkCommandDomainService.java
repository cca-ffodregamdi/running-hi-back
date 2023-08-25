package com.runninghi.bookmark.command.domain.service;

import com.runninghi.bookmark.command.domain.aggregate.vo.BookmarkVO;
import com.runninghi.common.annotation.DomainService;

public interface BookmarkCommandDomainService {
    void validatePostExist(Long postNo);
    void validateFolderExist(Long folderNo);
    void validateBookmarkExist(BookmarkVO bookmarkVO);
}
