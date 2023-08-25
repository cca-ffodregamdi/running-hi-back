package com.runninghi.bookmarkfolder.command.application.service;

import com.runninghi.bookmark.command.application.dto.request.DeleteBookmarkRequest;
import com.runninghi.bookmark.command.application.service.BookmarkCommandService;
import com.runninghi.bookmark.command.domain.aggregate.entity.Bookmark;
import com.runninghi.bookmark.command.domain.repository.BookmarkCommandRepository;
import com.runninghi.bookmark.query.infrastructure.repository.BookmarkQueryRepository;
import com.runninghi.bookmarkfolder.command.application.dto.request.CreateFolderRequest;
import com.runninghi.bookmarkfolder.command.application.dto.request.DeleteFolderRequest;
import com.runninghi.bookmarkfolder.command.application.dto.request.UpdateFolderRequest;
import com.runninghi.bookmarkfolder.command.application.dto.response.FolderCommandResponse;
import com.runninghi.bookmarkfolder.command.application.dto.response.FolderDeleteResponse;
import com.runninghi.bookmarkfolder.command.domain.aggregate.entity.BookmarkFolder;
import com.runninghi.bookmarkfolder.command.domain.repository.FolderCommandRepository;
import com.runninghi.bookmarkfolder.command.domain.service.FolderCommandDomainService;
import com.runninghi.common.handler.feedback.customException.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookmarkFolderCommandService {

    private final FolderCommandRepository folderRepository;
    private final BookmarkCommandRepository bookmarkRepository;
    private final BookmarkQueryRepository bookmarkQueryRepository;
    private final BookmarkCommandService bookmarkCommandService;
    private final FolderCommandDomainService domainService;
    @Transactional
    public FolderCommandResponse createNewBookmarkFolder(CreateFolderRequest folderDTO) {

        domainService.validateFolderName(folderDTO.folderName());

        BookmarkFolder folder = folderRepository.save(BookmarkFolder.builder().
                folderName(folderDTO.folderName()).
                userNo(folderDTO.userNo()).
                build());

        return FolderCommandResponse.from(folder);
    }

    @Transactional
    public FolderDeleteResponse deleteBookmarkFolder(DeleteFolderRequest folderDTO) {

        domainService.validateFolderExist(folderDTO.folderNo());

        List<Bookmark> bookmarks = bookmarkQueryRepository.findBookmarkByBookmarkVO_FolderNo(folderDTO.folderNo());
        bookmarks.forEach(bookmark -> bookmarkCommandService.deleteBookmark(new DeleteBookmarkRequest(bookmark.getBookmarkVO())));


        folderRepository.deleteById(folderDTO.folderNo());

        return new FolderDeleteResponse(true);

    }

    @Transactional
    public FolderCommandResponse updateBookmarkFolder(UpdateFolderRequest folderDTO) {

        domainService.validateFolderExist(folderDTO.folderNo());
        domainService.validateFolderName(folderDTO.folderName());

        BookmarkFolder folder = folderRepository.findById(folderDTO.folderNo())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 폴더입니다."));

        folder.update(folderDTO);

        return FolderCommandResponse.from(folder);
    }

}
