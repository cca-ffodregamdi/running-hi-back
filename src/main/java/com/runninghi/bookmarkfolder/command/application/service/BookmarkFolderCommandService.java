package com.runninghi.bookmarkfolder.command.application.service;

import com.runninghi.bookmark.command.application.dto.request.DeleteBookmarkRequest;
import com.runninghi.bookmark.command.application.service.BookmarkCommandService;
import com.runninghi.bookmark.command.domain.aggregate.entity.Bookmark;
import com.runninghi.bookmark.command.domain.repository.BookmarkRepository;
import com.runninghi.bookmarkfolder.command.application.dto.request.CreateFolderRequest;
import com.runninghi.bookmarkfolder.command.application.dto.request.DeleteFolderRequest;
import com.runninghi.bookmarkfolder.command.application.dto.request.UpdateFolderRequest;
import com.runninghi.bookmarkfolder.command.domain.aggregate.entity.BookmarkFolder;
import com.runninghi.bookmarkfolder.command.domain.repository.BookmarkFolderRepository;
import com.runninghi.bookmarkfolder.command.domain.service.FolderCommandDomainService;
import com.runninghi.common.handler.feedback.customException.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookmarkFolderCommandService {

    private final BookmarkFolderRepository folderRepository;
    private final BookmarkRepository bookmarkRepository;
    private final BookmarkCommandService bookmarkCommandService;
    private final FolderCommandDomainService domainService;
    @Transactional
    public void createNewBookmarkFolder(CreateFolderRequest folderDTO) {

        domainService.validateFolderName(folderDTO.folderName());

        folderRepository.save(BookmarkFolder.builder().
                folderName(folderDTO.folderName()).
                userNo(folderDTO.getUserNo()).
                build());
    }

    @Transactional
    public void deleteBookmarkFolder(DeleteFolderRequest folderDTO) {

        domainService.validateFolderExist(folderDTO.folderNo());

        List<Bookmark> bookmarks = bookmarkRepository.findBookmarkByBookmarkVO_FolderNo(folderDTO.folderNo());
        bookmarks.forEach(bookmark -> bookmarkCommandService.deleteBookmark(new DeleteBookmarkRequest(bookmark.getBookmarkVO())));


        folderRepository.deleteById(folderDTO.folderNo());

    }

    @Transactional
    public void updateBookmarkFolder(UpdateFolderRequest folderDTO) {

        domainService.validateFolderExist(folderDTO.folderNo());
        domainService.validateFolderName(folderDTO.folderName());

        BookmarkFolder folder = folderRepository.findById(folderDTO.folderNo())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 폴더입니다."));

        folder.update(folderDTO);
    }

}
