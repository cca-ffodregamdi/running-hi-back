package com.runninghi.bookmarkfolder.query.application.service;

import com.runninghi.bookmarkfolder.command.domain.aggregate.entity.BookmarkFolder;
import com.runninghi.bookmarkfolder.query.application.dto.request.FindFolderRequest;
import com.runninghi.bookmarkfolder.query.infrastructure.repository.FindFolderRepository;
import com.runninghi.common.annotation.DomainService;
import com.runninghi.feedback.command.domain.exception.customException.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FindBookmarkFolderService {

    private final FindFolderRepository findFolderRepository;

    @Transactional(readOnly=true)
    public BookmarkFolder findBookmarkFolder(FindFolderRequest folderRequest) {

        BookmarkFolder bookmarkFolder = findFolderRepository.findById(folderRequest.folderNo())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 폴더입니다."));

        return bookmarkFolder;
    }
}
