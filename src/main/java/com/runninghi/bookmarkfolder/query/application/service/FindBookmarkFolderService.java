package com.runninghi.bookmarkfolder.query.application.service;

import com.runninghi.bookmarkfolder.command.domain.aggregate.entity.BookmarkFolder;
import com.runninghi.bookmarkfolder.query.application.dto.request.FindFolderRequest;
import com.runninghi.bookmarkfolder.query.infrastructure.repository.FindFolderRepository;
import com.runninghi.feedback.command.domain.exception.customException.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FindBookmarkFolderService {

    private final FindFolderRepository findFolderRepository;

    @Transactional(readOnly=true)
    public Optional<BookmarkFolder> findBookmarkFolder(FindFolderRequest folderRequest) {

        Optional<BookmarkFolder> bookmarkFolder = findFolderRepository.findById(folderRequest.folderNo());

        if(!bookmarkFolder.isPresent()) {
            throw new NotFoundException("해당 폴더가 존재하지 않습니다.");
        } else {
            return bookmarkFolder;
        }
    }
}
