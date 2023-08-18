package com.runninghi.bookmarkfolder.query.infrastructure.service;
import com.runninghi.bookmarkfolder.command.domain.aggregate.entity.BookmarkFolder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


public interface FindBookmarkFolder {
    @Transactional(readOnly=true)
    Optional<BookmarkFolder> findBookmarkFolder(Long folderNo);
}
