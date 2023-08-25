package com.runninghi.bookmark.command.application.service;

import com.runninghi.bookmark.command.application.dto.request.CreateBookmarkRequest;
import com.runninghi.bookmark.command.application.dto.request.DeleteBookmarkRequest;
import com.runninghi.bookmark.command.domain.aggregate.entity.Bookmark;
import com.runninghi.bookmark.command.domain.aggregate.vo.BookmarkUserVO;
import com.runninghi.bookmark.command.domain.repository.BookmarkRepository;
import com.runninghi.bookmark.command.domain.service.BookmarkCommandDomainService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class BookmarkCommandService {

    private final BookmarkRepository bookmarkRepository;
    private final BookmarkCommandDomainService domainService;

    @Transactional
    public Bookmark createBookmark(CreateBookmarkRequest bookmarkDTO) {

        //infraService.validatePostExist(bookmarkDTO.bookmarkVO().getPostNo());
        domainService.validateFolderExist(bookmarkDTO.bookmarkVO().getFolderNo());

        return bookmarkRepository.save(Bookmark.builder()
                .bookmarkVO(bookmarkDTO.bookmarkVO())
                .userNo(bookmarkDTO.userNo())
                .addDate(LocalDate.now())
                .build());
    }

    @Transactional
    public void deleteBookmark(DeleteBookmarkRequest bookmarkDTO) {

        domainService.validateFolderExist(bookmarkDTO.bookmarkVO().getFolderNo());

        bookmarkRepository.deleteById(bookmarkDTO.bookmarkVO());
    }

}
