package com.runninghi.bookmarkfolder.command.application.service;

import com.runninghi.bookmarkfolder.command.application.dto.request.DeleteFolderRequest;
import com.runninghi.bookmarkfolder.command.domain.aggregate.entity.BookmarkFolder;
import com.runninghi.bookmarkfolder.command.domain.repository.BookmarkFolderRepository;
import com.runninghi.bookmarkfolder.command.domain.service.BookmarkFolderDomainService;
import com.runninghi.common.annotation.DomainService;
import com.runninghi.feedback.command.domain.exception.customException.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
public class DeleteBookmarkFolderService implements BookmarkFolderDomainService {

    private final BookmarkFolderRepository folderRepository;

    @Transactional
    public void deleteBookmarkFolder(DeleteFolderRequest folderDTO) {

        BookmarkFolder folder = folderRepository.findById(folderDTO.folderNo())
                .orElseThrow(() -> new NotFoundException("존재하지않는 회원입니다."));

        folderRepository.deleteById(folder.getFolderNo());

    }
}
