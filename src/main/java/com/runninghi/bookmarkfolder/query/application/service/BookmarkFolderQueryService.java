package com.runninghi.bookmarkfolder.query.application.service;

import com.runninghi.bookmarkfolder.command.domain.aggregate.entity.BookmarkFolder;
import com.runninghi.bookmarkfolder.query.application.dto.request.FindFolderRequest;
import com.runninghi.bookmarkfolder.query.infrastructure.repository.FindFolderRepository;
import com.runninghi.common.handler.feedback.customException.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BookmarkFolderQueryService {

    private final FindFolderRepository findFolderRepository;

    @Transactional(readOnly=true)
    public BookmarkFolder findBookmarkFolder(FindFolderRequest folderRequest) {

        return findFolderRepository.findById(folderRequest.folderNo())
                .orElseThrow(() -> new NotFoundException("해당 폴더가 존재하지 않습니다."));
    }
}
