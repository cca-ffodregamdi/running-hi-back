package com.runninghi.bookmarkfolder.query.application.service;

import com.runninghi.bookmark.command.domain.aggregate.entity.Bookmark;
import com.runninghi.bookmarkfolder.command.domain.aggregate.entity.BookmarkFolder;
import com.runninghi.bookmarkfolder.query.infrastructure.repository.FindFolderRepository;
import com.runninghi.bookmarkfolder.query.infrastructure.service.FindBookmarkFolder;
import com.runninghi.feedback.command.domain.exception.customException.IllegalArgumentException;
import com.runninghi.feedback.command.domain.exception.customException.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FindBookmarkFolderImpl implements FindBookmarkFolder {

    private final FindFolderRepository findFolderRepository;

    @Override
    public Optional<BookmarkFolder> findBookmarkFolder(Long folderNo) {

        Optional<BookmarkFolder> bookmarkFolder = findFolderRepository.findById(folderNo);

        if(!bookmarkFolder.isPresent()) {
            throw new NotFoundException("해당 폴더가 존재하지 않습니다.");
        } else {
            return bookmarkFolder;
        }
    }
}
