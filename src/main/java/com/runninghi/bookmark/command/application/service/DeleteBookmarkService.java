package com.runninghi.bookmark.command.application.service;

import com.runninghi.bookmark.command.application.dto.request.DeleteBookmarkRequest;
import com.runninghi.bookmark.command.domain.aggregate.entity.Bookmark;
import com.runninghi.bookmark.command.domain.repository.BookmarkRepository;
import com.runninghi.bookmark.command.domain.service.BookmarkDomainService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteBookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final BookmarkDomainService domainService;
    @Transactional
    public void deleteBookmark(DeleteBookmarkRequest bookmarkDTO) {

        domainService.validateFolderExist(bookmarkDTO.bookmarkVO().getFolderNo());

        bookmarkRepository.deleteById(bookmarkDTO.bookmarkVO());
    }


}
